package cn.basewin.unionpay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.xutils.ex.DbException;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.base.KeyValueAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.IDUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/1 10:57<br>
 * 描述: 核对交易信息的一个类 <br>
 */
public class CheckInfoAty extends KeyValueAty {
    public static final String KEY_DATA = "CheckInfoData";
    public static final int resultCode = AppConfig.RESULT_CODE_CHECK_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDate(false);
        TransactionData data = getData();
        String[] strings = UIDataHelper.CheckInfoAty();
        String[] ss = new String[5];
        ss[0] = data.getName();
        ss[1] = TDevice.hiddenCardNum(data.getPan());
        ss[2] = PosUtil.centToYuan(data.getAmount());
        ss[3] = data.getTrace();
        ss[4] = data.getReferenceNo();
        List<Map> maps = UIDataHelper.setListMap_Vaule(strings, ss);
        TLog.l(maps.toString());
        setAdapterData(maps);
    }

    private void setDate(boolean s) {
        Intent intent = new Intent();
        intent.putExtra(KEY_DATA, s);
        setResult(resultCode, intent);
    }

    @Override
    protected void onClickOK() {
        super.onClickOK();
        setDate(true);
        boolean swipingCard = IDUtil.needSwipeCard(FlowControl.MapHelper.getAction());
        if (!swipingCard) {
            removeNextFlow();
        }
        startNextFlow();
    }

    @Override
    protected void onClickUnOK() {
        super.onClickUnOK();
        finish();
    }

    /**
     * 通过流程数据拿到参考号 去取出数据
     */
    private TransactionData getData() {
        String certificate = FlowControl.MapHelper.getTrace();
        if (TextUtils.isEmpty(certificate)) {
            DialogHelper.showAndClose(this, "参考号为空！");
            return null;
        }
        TransactionData tranByTraceNO = null;
        try {
            tranByTraceNO = TransactionDataDao.getTranByTraceNO(certificate);
        } catch (DbException e) {
            e.printStackTrace();
            DialogHelper.showAndClose(this, "数据库异常，请重试！");
        }
        if (tranByTraceNO == null) {
            DialogHelper.showAndClose(this, "没凭证号:" + certificate + " 的有这比交易！");
            return tranByTraceNO;
        }
        return tranByTraceNO;
    }
}
