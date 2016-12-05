package cn.basewin.unionpay.setting;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.ListDialog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 16:42<br>
 * 描述：
 */
public class CommuParmDialFragment extends BaseSysFragment {
    private static final String TAG = CommuParmDialFragment.class.getName();
    /**
     * 外线号码
     */
    private static final String KEY_OUTSIDE_CALL = TAG + "outside_call";
    /**
     * 主机电话1
     */
    private static final String KEY_MAIN_PHONE_NUM_1 = TAG + "main_phone_num_1";
    /**
     * 主机电话2
     */
    private static final String KEY_MAIN_PHONE_NUM_2 = TAG + "main_phone_num_2";
    /**
     * 主机电话3
     */
    private static final String KEY_MAIN_PHONE_NUM_3 = TAG + "main_phone_num_3";
    /**
     * AT指令
     */
    private static final String KEY_AT_ORDER = TAG + "at_order";
    /**
     * 拨号方式
     */
    private static final String KEY_DIAL_STYLE = TAG + "dial_style";
    /**
     * Patch
     */
    private static final String KEY_PATCH = TAG + "patch";
    /**
     * 外线号码
     */
    private EditText et_outside_call;
    /**
     * 主机电话
     */
    private EditText et_main_phone_num_1, et_main_phone_num_2, et_main_phone_num_3;
    /**
     * AT指令
     */
    private EditText et_at_order;
    /**
     * 拨号方式
     */
    private TextView tv_dial_style;
    /**
     * Patch
     */
    private EditText et_pathch;
    private String[] dial_type = {"脉冲", "音频"};

    private void initViews(View view) {
        et_outside_call = (EditText) view.findViewById(R.id.et_outside_call);
        et_main_phone_num_1 = (EditText) view.findViewById(R.id.et_main_phone_num_1);
        et_main_phone_num_2 = (EditText) view.findViewById(R.id.et_main_phone_num_2);
        et_main_phone_num_3 = (EditText) view.findViewById(R.id.et_main_phone_num_3);
        et_at_order = (EditText) view.findViewById(R.id.et_at_order);
        et_pathch = (EditText) view.findViewById(R.id.et_pathch);
        tv_dial_style = (TextView) view.findViewById(R.id.tv_dial_style);
    }

    private void initData() {
        et_outside_call.setText(getOutsideCall());
        et_main_phone_num_1.setText(getMainPhoneNum1());
        et_main_phone_num_2.setText(getMainPhoneNum2());
        et_main_phone_num_3.setText(getMainPhoneNum3());
        et_at_order.setText(getAtOrder());
        et_pathch.setText(getPatch());
        tv_dial_style.setText(getDialStyle());
    }

    private void setListener() {
        tv_dial_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new ListDialog(mContext, tv_dial_style.getText().toString(), dial_type, new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_dial_style.setText(str);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_sysset_commuparm_dial;
    }

    @Override
    public void afterGetView(View view) {
        initViews(view);
        initData();
        setListener();
    }

    @Override
    public void save() {
        SPTools.set(CommuParmDialFragment.KEY_OUTSIDE_CALL, et_outside_call.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_1, et_main_phone_num_1.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_2, et_main_phone_num_2.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_3, et_main_phone_num_3.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_AT_ORDER, et_at_order.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_PATCH, et_pathch.getText().toString());
        SPTools.set(CommuParmDialFragment.KEY_DIAL_STYLE, tv_dial_style.getText().toString());
        CommuParmAty.setCommuType("拨号");
    }

    public static String getOutsideCall() {
        return SPTools.get(CommuParmDialFragment.KEY_OUTSIDE_CALL, AppConfig.DEFAULT_VALUE_OUTSIDE_CALL);
    }

    public static void setOutsideCall(String oc) {
        SPTools.set(CommuParmDialFragment.KEY_OUTSIDE_CALL, oc);
    }

    public static String getMainPhoneNum1() {
        return SPTools.get(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_1, AppConfig.DEFAULT_VALUE_MAIN_PHONE_NUM_1);
    }

    public static String getMainPhoneNum2() {
        return SPTools.get(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_2, AppConfig.DEFAULT_VALUE_MAIN_PHONE_NUM_2);
    }

    public static String getMainPhoneNum3() {
        return SPTools.get(CommuParmDialFragment.KEY_MAIN_PHONE_NUM_3, AppConfig.DEFAULT_VALUE_MAIN_PHONE_NUM_3);
    }

    public static String getAtOrder() {
        return SPTools.get(CommuParmDialFragment.KEY_AT_ORDER, AppConfig.DEFAULT_VALUE_AT_ORDER);
    }

    public static String getDialStyle() {
        return SPTools.get(CommuParmDialFragment.KEY_DIAL_STYLE, AppConfig.DEFAULT_VALUE_DIAL_STYLE);
    }

    public static String getPatch() {
        return SPTools.get(CommuParmDialFragment.KEY_PATCH, AppConfig.DEFAULT_VALUE_PATCH);
    }
}