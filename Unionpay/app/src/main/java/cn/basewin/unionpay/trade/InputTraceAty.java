package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/30 18:22<br>
 * 描述: 输入凭证号 <br>
 */
public class InputTraceAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_CERTIFICATE;
    public static final String KEY_DATA = "certificate_code";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_certificate));
        setLabel2("");
        setLenghtEdit(6);
        setRule(BaseInputAty.rule_number);
        if (TLog.isTest) {
            TransactionDataDao.showDbAll();
        }
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s)) {
            TLog.l("输入凭证号：" + s);
            s = PosUtil.numToStr6(s);
            TLog.l("fromat凭证号：" + s);
            TransactionData t = null;
            try {
                t = TransactionDataDao.getTranByTraceNO(s);
            } catch (DbException e) {
                e.printStackTrace();
                return;
            }
            if (t == null) {
                DialogHelper.showAndClose(this, getString(R.string.bucunzai));
                return;
            } else {
                if ("-".equals(t.getStatus())) {
                    DialogHelper.showAndClose(this, getString(R.string.bunengchexiao));
                    return;
                }
                try {
                    Card card = FlowControl.MapHelper.getCard();
                    card.setPan(t.getPan());
                    card.setMoney(t.getAmount());
                    card.setExpDate(t.getExpDate());
                    FlowControl.MapHelper.setMoney(PosUtil.changeAmout(t.getAmount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            FlowControl.MapHelper.setTrace(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_certificate));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }


}
