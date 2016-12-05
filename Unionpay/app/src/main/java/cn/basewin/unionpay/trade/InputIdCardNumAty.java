package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/22 13:01<br>
 * 描述：获取证件信息
 */
public class InputIdCardNumAty extends BaseFlowAty implements CompoundButton.OnCheckedChangeListener {
    public static final String KEY_ID_CARD = "InputIdCardNumAty_id_card";
    public static final String KEY_ID_TYPE = "InputIdCardNumAty_id_type";
    private CheckBox cb_idcard, cb_passport, cb_taiwan, cb_shibing, cb_junguan, cb_huixiang, cb_police, cb_other;
    private List<CheckBox> mList = new ArrayList<>();
    private Button btn_finish, btn_confirm;
    private EditText et_input_txt;
    private String type = "01";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_inputidcard);
        initTitle();
        initViews();
        setListener();
    }

    private void initViews() {
        cb_idcard = (CheckBox) findViewById(R.id.cb_idcard);
        cb_idcard.setTag("01");
        cb_passport = (CheckBox) findViewById(R.id.cb_passport);
        cb_passport.setTag("03");
        cb_taiwan = (CheckBox) findViewById(R.id.cb_taiwan);
        cb_taiwan.setTag("05");
        cb_shibing = (CheckBox) findViewById(R.id.cb_shibing);
        cb_shibing.setTag("07");
        cb_junguan = (CheckBox) findViewById(R.id.cb_junguan);
        cb_junguan.setTag("02");
        cb_huixiang = (CheckBox) findViewById(R.id.cb_huixiang);
        cb_huixiang.setTag("04");
        cb_police = (CheckBox) findViewById(R.id.cb_police);
        cb_police.setTag("06");
        cb_other = (CheckBox) findViewById(R.id.cb_other);
        cb_other.setTag("99");
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_input_txt = (EditText) findViewById(R.id.et_input_txt);
        mList.add(cb_idcard);
        mList.add(cb_passport);
        mList.add(cb_taiwan);
        mList.add(cb_shibing);
        mList.add(cb_junguan);
        mList.add(cb_huixiang);
        mList.add(cb_police);
        mList.add(cb_other);
    }

    private void setListener() {
        for (CheckBox cb : mList) {
            cb.setOnCheckedChangeListener(this);
        }

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_input_txt.getText().toString();
                if (TextUtils.isEmpty(id)) {
                    TLog.showToast("证件号码不能为空");
                    return;
                }
                FlowControl.MapHelper.getMap().put(InputIdCardNumAty.KEY_ID_CARD, id);
                FlowControl.MapHelper.getMap().put(InputIdCardNumAty.KEY_ID_TYPE, type);
                startNextFlow();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            type = (String) buttonView.getTag();
            for (CheckBox cb : mList) {
                if (cb != buttonView) {
                    cb.setChecked(false);
                }
            }
        }
    }
}
