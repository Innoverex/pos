package cn.basewin.unionpay.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import cn.basewin.unionpay.R;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/28 17:27<br>
 * 描述：打印参数
 */
public class PrintParamAty extends BaseSysSettingAty implements View.OnClickListener {
    private Button btn_print_merchantparam, btn_print_tradecontrol, btn_print_syscontrol, btn_print_commucontrol, btn_print_other, btn_print_version;

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_printparam;
    }

    @Override
    public String getAtyTitle() {
        return "打印参数";
    }

    @Override
    public void afterSetContentView() {
        initViews();
        setListener();
        setRightBtnVisible(false);
    }

    @Override
    public void save() {

    }

    private void initViews() {
        btn_print_merchantparam = (Button) findViewById(R.id.btn_print_merchantparam);
        btn_print_tradecontrol = (Button) findViewById(R.id.btn_print_tradecontrol);
        btn_print_syscontrol = (Button) findViewById(R.id.btn_print_syscontrol);
        btn_print_commucontrol = (Button) findViewById(R.id.btn_print_commucontrol);
        btn_print_other = (Button) findViewById(R.id.btn_print_other);
        btn_print_version = (Button) findViewById(R.id.btn_print_version);
    }

    private void setListener() {
        btn_print_merchantparam.setOnClickListener(this);
        btn_print_tradecontrol.setOnClickListener(this);
        btn_print_syscontrol.setOnClickListener(this);
        btn_print_commucontrol.setOnClickListener(this);
        btn_print_other.setOnClickListener(this);
        btn_print_version.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PrintParamAty.this, SysSettingPrintWaitAty.class);
        intent.putExtra(SysSettingPrintWaitAty.KEY_SYS_PRINT_ID, v.getId());
        startActivity(intent);
    }


}
