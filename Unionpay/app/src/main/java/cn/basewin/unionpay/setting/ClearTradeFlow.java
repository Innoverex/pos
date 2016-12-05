package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.Reverse;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/28 18:57<br>
 * 描述：清除冲正标志/交易流水
 */
public class ClearTradeFlow extends BaseSysSettingAty implements View.OnClickListener {
    private Button btn_clear_tradeflow, btn_print_chongzheng;


    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_cleartradeaty;
    }

    @Override
    public String getAtyTitle() {
        return "清除数据";
    }

    @Override
    public void afterSetContentView() {
        initViews();
        setRightBtnVisible(false);
        setListener();
    }

    @Override
    public void save() {

    }

    private void initViews() {
        btn_clear_tradeflow = (Button) findViewById(R.id.btn_clear_tradeflow);
        btn_print_chongzheng = (Button) findViewById(R.id.btn_print_chongzheng);
    }

    private void setListener() {
        btn_clear_tradeflow.setOnClickListener(this);
        btn_print_chongzheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear_tradeflow:
                try {
                    DbManager db = AppContext.db();
                    db.delete(TransactionData.class);
                    Toast.makeText(ClearTradeFlow.this, "清除交易流水成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_print_chongzheng:
                Reverse.setReverseFlag(false);
                Toast.makeText(ClearTradeFlow.this, "清除冲正标志成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
