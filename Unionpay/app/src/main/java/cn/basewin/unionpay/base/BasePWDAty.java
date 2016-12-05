package cn.basewin.unionpay.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.basewin.aidl.OnPinInputListener;
import com.basewin.define.OutputPBOCAAData;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BCDHelper;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.setting.TradeInputPwdSettingAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.VirtualPWKeyboardView;

/**
 * 内容摘要: <br>
 * 创建时间:  2016/7/19 09:33<br>
 * 描述: 金额，卡号，这2个属性必须带入进来 这是这个页面独有的属性，卡号是需要传入到sdk加密的 <br>
 * 描述: 密码长度可以带过来，如果没有会去读取默认的密码长度<br>
 */
public class BasePWDAty extends BaseFlowAty implements OnPinInputListener {
    private static final String TAG = BasePWDAty.class.getName();
    private TextView tv_pw_dialog_money, tv_pw_dialog_card, tv_title;
    private VirtualPWKeyboardView vpw_dialog_pw;
    private List<TextView> list_pins = null;
    private TextView pin1, pin2, pin3, pin4, pin5, pin6;
    public static final String PW_HINT = "*";//密码屏蔽的符号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pw_keyboard);
        TLog.l("BasePWDAty");
        TLog.e(TAG, TDevice.getMinTime());
        if (!TradeInputPwdSettingAty.select(FlowControl.MapHelper.getAction())) {
            try {
                TLog.l("不走密码 是ic卡的话就自动确认密码 进入下一个流程");
                if (FlowControl.MapHelper.getCard().isIC()) {
                    ServiceManager.getInstence().getPboc().comfirmPinpad(null);
                }
            } catch (Exception e) {
                TLog.showToast("系统异常，请重试！");
                e.printStackTrace();
            }
            startNextFlow();
            finish();
            return;
        }
        initview();
        tv_pw_dialog_money = (TextView) findViewById(R.id.tv_pw_dialog_money);
        tv_pw_dialog_card = (TextView) findViewById(R.id.tv_pw_dialog_card);
        tv_title = (TextView) findViewById(R.id.tv_title);

        vpw_dialog_pw = (VirtualPWKeyboardView) findViewById(R.id.vpw_dialog_pw);
        if (AppConfig.hasPhysicalKey()) {
            vpw_dialog_pw.setVisibility(View.GONE);
        }
        //隐藏layout里面的图片，保持占位，避免title不能居中对齐
        hideLeftKeepMargin();
        int action = FlowControl.MapHelper.getAction();
        setTitle(TLog.getString(ActionConstant.getAction(action)));
        if (action != ActionConstant.ACTION_QUERY_BALANCE) {
            setMoney(FlowControl.MapHelper.getMoney());
        }
        Card card = FlowControl.MapHelper.getCard();
        String pan = null;
        if (card != null) {
            pan = card.getPan();
        }
        if (TextUtils.isEmpty(pan)) {
            TLog.e(TAG, "托管密码键盘时卡号为空。");
            this.finish();
            return;
        }
        setCard(pan);
        startPW(pan);
        setTimerFlag(false);
    }

    protected void initview() {
        pin1 = (TextView) findViewById(R.id.tx_pin01);
        pin2 = (TextView) findViewById(R.id.tx_pin02);
        pin3 = (TextView) findViewById(R.id.tx_pin03);
        pin4 = (TextView) findViewById(R.id.tx_pin04);
        pin5 = (TextView) findViewById(R.id.tx_pin05);
        pin6 = (TextView) findViewById(R.id.tx_pin06);
        list_pins = new ArrayList<TextView>();
        list_pins.add(pin1);
        list_pins.add(pin2);
        list_pins.add(pin3);
        list_pins.add(pin4);
        list_pins.add(pin5);
        list_pins.add(pin6);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startPW(String card_pan) {
        try {
            TLog.e(TAG, "startPW");
            TLog.e(TAG, TDevice.getMinTime());
            ServiceManager.getInstence().getPinpad().setOnPinInputListener(this);
            ServiceManager.getInstence().getPinpad().inputOnlinePin(card_pan, AppConfig.POS_PW_LENGTH);
        } catch (Exception e) {
            TLog.e(TAG, "startPW error");
            finish();
            e.printStackTrace();
        }
    }

    public void setTitle(String msg) {
        tv_title.setText(msg);
    }

    public void setMoney(String msg) {
        tv_pw_dialog_money.setText(getString(R.string.jine) + ":" + msg);
    }

    /**
     * 设置卡号
     *
     * @param cardId
     */
    public void setCard(String cardId) {
        StringBuilder sb = new StringBuilder(cardId.substring(0, 6));
        for (int i = 0; i < cardId.length() - 10; i++) {
            sb.append("*");
        }
        sb.append(cardId.substring(cardId.length() - 4, cardId.length()));
        tv_pw_dialog_card.setText(getString(R.string.kahao) + ":" + sb.toString());
    }

    public void setPW(int cardId) {
        for (int i = 0; i < list_pins.size(); i++) {
            if (i < cardId) {
                list_pins.get(i).setText(PW_HINT);
            } else {
                list_pins.get(i).setText(null);
            }
        }
    }

    @Override
    public void onInput(int len, int key) throws RemoteException {
        TLog.e(TAG, "onInput: len:" + len + "   key:+" + key);
        setPW(len);
    }

    @Override
    public void onError(int i) throws RemoteException {
        TLog.e(TAG, "键盘onError:" + i);
        finish();
    }

    @Override
    public void onConfirm(byte[] bytes, boolean b) throws RemoteException {
        TLog.e(TAG, "onConfirm");
        try {
            Card card = FlowControl.MapHelper.getCard();
            if (null != card) {
                if (card.isICCard()) {
                    TLog.l("是 ic 插卡");
                    ServiceManager.getInstence().getPboc().comfirmPinpad(bytes);
                } else {
                    TLog.l("不是 ic 插卡");
                }
                if (bytes != null) {
                    TLog.e(TAG, "bytes=" + BCDHelper.bcdToString(bytes, 0, bytes.length));
                }
            }
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel() throws RemoteException {
        TLog.e(TAG, "onCancel");
        finish();
    }

    @Override
    public void onPinpadShow(byte[] bytes) throws RemoteException {
        TLog.e(TAG, "onPinpadShow");
        TLog.e(TAG, TDevice.getMinTime());
        if (bytes == null) {
            onError(0);
            return;
        }
        TLog.l("底层返回键盘随机码：" + BCDHelper.hex2DebugHexString(bytes, bytes.length));
        try {
            vpw_dialog_pw.setKeyShow(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAARequestOnlineProcess(Intent intent) throws RemoteException {
        TLog.e(TAG, "onAARequestOnlineProcess");
        OutputPBOCAAData out = new OutputPBOCAAData(intent);
        Card card = FlowControl.MapHelper.getCard();
        if (card != null) {
            card.icAAData = out;
            try {
                byte[] tc = ServiceManager.getInstence().getPboc().getEmvTlvData(0X9F26);
                card.setTC(tc);
            } catch (Exception e) {
                onError(new Intent());
                e.printStackTrace();
            }
        }
    }
}
