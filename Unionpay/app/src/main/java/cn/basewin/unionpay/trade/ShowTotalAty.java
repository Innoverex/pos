package cn.basewin.unionpay.trade;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xutils.ex.DbException;

import java.math.BigInteger;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.PosUtil;

public class ShowTotalAty extends Activity {
    /**
     * domestic 国内
     * foreign 国外
     * <p/>
     * debit 借记
     * credit 贷记
     */
    private static final String TAG = "ShowTotalAty";
    private TextView tv_domestic_debit_amount;
    private TextView tv_domestic_debit_acount;
    private TextView tv_domestic_credit_amount;
    private TextView tv_domestic_credit_acount;
    private TextView tv_foreign_debit_amount;
    private TextView tv_foreign_debit_acount;
    private TextView tv_foreign_credit_amount;
    private TextView tv_foreign_credit_acount;

    private BigInteger ddAmount;//金额
    private BigInteger dcAmount;
    private BigInteger fdAmount;
    private BigInteger fcAmount;
    private int ddAcount = 0;//笔数
    private int dcAcount = 0;
    private int fdAcount = 0;
    private int fcAcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_total_aty);
        initView();
        initData();
    }

    private void initView() {
        Log.i(TAG, "initUI...");
        tv_domestic_debit_amount = (TextView) findViewById(R.id.tv_domestic_debit_amount);
        tv_domestic_debit_acount = (TextView) findViewById(R.id.tv_domestic_debit_acount);
        tv_domestic_credit_amount = (TextView) findViewById(R.id.tv_domestic_credit_amount);
        tv_domestic_credit_acount = (TextView) findViewById(R.id.tv_domestic_credit_acount);
        tv_foreign_debit_amount = (TextView) findViewById(R.id.tv_foreign_debit_amount);
        tv_foreign_debit_acount = (TextView) findViewById(R.id.tv_foreign_debit_acount);
        tv_foreign_credit_amount = (TextView) findViewById(R.id.tv_foreign_credit_amount);
        tv_foreign_credit_acount = (TextView) findViewById(R.id.tv_foreign_credit_acount);
    }

    private void initData() {
        Log.i(TAG, "initData...");
        ddAmount = new BigInteger("000000000000");
        dcAmount = new BigInteger("000000000000");
        fdAmount = new BigInteger("000000000000");
        fcAmount = new BigInteger("000000000000");
        sumData();
        setView();
    }

    private void setView() {
        Log.i(TAG, "setView...");
        tv_domestic_debit_amount.setText(PosUtil.changeAmout(ddAmount.toString()));
        tv_domestic_debit_acount.setText(ddAcount + "");
        tv_domestic_credit_amount.setText(PosUtil.changeAmout(dcAmount.toString()));
        tv_domestic_credit_acount.setText(dcAcount + "");
        tv_foreign_debit_amount.setText(PosUtil.changeAmout(fdAmount.toString()));
        tv_foreign_debit_acount.setText(fdAcount + "");
        tv_foreign_credit_amount.setText(PosUtil.changeAmout(fcAmount.toString()));
        tv_foreign_credit_acount.setText(fcAcount + "");
    }

    private void sumData() {
        Log.i(TAG, "sumData...");
        List<TransactionData> ls = null;
        try {
            ls = TransactionDataDao.selectAllValid();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (ls == null || ls.size() < 1) {
            Log.e(TAG, "无交易");
            return;
        }
        Log.i(TAG, "trans[" + ls.size() + "]=" + ls);
        for (TransactionData td : ls) {
            /**
             * 结算类型, 1借记、2贷记、其它都是结算无关
             * 卡类型, 1内卡、2外卡
             */
            if ("1".equals(td.getCardType())) {
                if ("1".equals(td.getSettleType())) {
                    ddAmount = ddAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    ddAcount++;
                } else if ("2".equals(td.getSettleType())) {
                    dcAmount = dcAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    dcAcount++;
                } else {
                    Log.e(TAG, "结算类型不匹配 TransactionData=" + td);
                }
            } else if ("2".equals(td.getCardType())) {
                if ("1".equals(td.getSettleType())) {
                    fdAmount = fdAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    fdAcount++;
                } else if ("2".equals(td.getSettleType())) {
                    fcAmount = fcAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    fcAcount++;
                } else {
                    Log.e(TAG, "结算类型不匹配 TransactionData=" + td);
                }
            } else {
                Log.e(TAG, "卡类型不匹配 TransactionData=" + td);
            }
        }
    }
}
