package cn.basewin.unionpay.setting;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.CustomInputDialog;


/**
 * 作者：lhc
 * 创建时间：2016/7/13 16:16
 * 描述：商户参数设置
 */
public class MerchantSetting extends BaseSysSettingAty {
    /**
     * 商户号
     */
    private static final String KEY_MERCHANT_NO = "merchant_no";
    /**
     * 终端号
     */
    private static final String KEY_TERMINAL_NO = "terminal_no";
    /**
     * 商户名
     */
    private static final String KEY_MERCHANT_NAME = "merchant_name";
    /**
     * 英文名
     */
    private static final String KEY_MERCHANT_EN_NAME = "merchant_en_name";
    /**
     * 子应用名
     */
    private static final String KEY_SUB_APP_NAME = "sub_app_name";
    /**
     * 商户号
     */
    private EditText et_merchant_no;
    /**
     * 终端号
     */
    private EditText et_terminal_no;
    /**
     * 商户名
     */
    private EditText et_merchant_name;
    /**
     * 英文名
     */
    private EditText et_merchant_eng_name;
    /**
     * 子应用名
     */
    private EditText et_sub_app_name;
    private CustomInputDialog mDialog;

    private void initViews() {
        et_merchant_no = (EditText) findViewById(R.id.et_shanghuhao);
        et_terminal_no = (EditText) findViewById(R.id.et_zhongduanhao);
        et_merchant_name = (EditText) findViewById(R.id.et_shanghuming);
        et_merchant_eng_name = (EditText) findViewById(R.id.et_yingwenming);
        et_sub_app_name = (EditText) findViewById(R.id.et_ziyingyongming);
        mDialog = new CustomInputDialog(mContext, R.style.Dialog_Fullscreen_title);
        mDialog.setRdMsg("请输入安全密码")
                .setRdLeftButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                }).setRdRightButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismissWithReturnResult();
            }
        }).setEditText(InputType.TYPE_TEXT_VARIATION_PASSWORD, new CustomInputDialog.RedialogInputResult() {
            @Override
            public void InputResult(String str) {
                String securePwd = SPTools.get(SysPwdMangeAty.KEY_SAVE_PWD, AppConfig.DEFAULT_VALUE_SAVE_PWD);
                if (str.equals(securePwd)) {
                    saveData();
                } else {
                    Toast.makeText(mContext, "密码错误，保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, 6);
    }

    private void initData() {
        et_merchant_no.setText(SPTools.get(MerchantSetting.KEY_MERCHANT_NO, ""));
        et_terminal_no.setText(SPTools.get(MerchantSetting.KEY_TERMINAL_NO, ""));
        et_merchant_name.setText(SPTools.get(MerchantSetting.KEY_MERCHANT_NAME, ""));
        et_merchant_eng_name.setText(SPTools.get(MerchantSetting.KEY_MERCHANT_EN_NAME, ""));
        et_sub_app_name.setText(SPTools.get(MerchantSetting.KEY_SUB_APP_NAME, ""));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_shanghucanshu;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.shanghucanshushezhi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        String merchantNo = et_merchant_no.getText().toString();//商户号
        if (merchantNo.length() != 15) {
            Toast.makeText(mContext, "请输入15位商户号", Toast.LENGTH_SHORT).show();
            return;
        }
        String terminalNo = et_terminal_no.getText().toString();//终端号
        if (terminalNo.length() != 8) {
            Toast.makeText(mContext, "请输入8位终端号", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.show();
    }

    private void saveData() {
        String merchantNo = et_merchant_no.getText().toString();//商户号
        String terminalNo = et_terminal_no.getText().toString();//终端号
        SPTools.set(MerchantSetting.KEY_MERCHANT_NO, merchantNo);
        SPTools.set(MerchantSetting.KEY_TERMINAL_NO, terminalNo);
        SPTools.set(MerchantSetting.KEY_MERCHANT_NAME, et_merchant_name.getText().toString());
        SPTools.set(MerchantSetting.KEY_MERCHANT_EN_NAME, et_merchant_eng_name.getText().toString());
        SPTools.set(MerchantSetting.KEY_SUB_APP_NAME, et_sub_app_name.getText().toString());
        showModifyHint();
    }

    public static String getMerchantNo() {
        return SPTools.get(MerchantSetting.KEY_MERCHANT_NO, AppConfig.DEFAULT_VALUE_MERCHANT_NO);
    }

    public static String getTerminalNo() {
        return SPTools.get(MerchantSetting.KEY_TERMINAL_NO, AppConfig.DEFAULT_VALUE_TERMINAL_NO);
    }

    public static void setMerchantNo(String no) {
        SPTools.set(MerchantSetting.KEY_MERCHANT_NO, no);
    }

    public static void setTerminalNo(String no) {
        SPTools.set(MerchantSetting.KEY_TERMINAL_NO, no);
    }

    public static String getMerchantName() {
        return SPTools.get(MerchantSetting.KEY_MERCHANT_NAME, AppConfig.DEFAULT_VALUE_MERCHANT_NAME);
    }

    public static String getMerchantEnName() {
        return SPTools.get(MerchantSetting.KEY_MERCHANT_EN_NAME, AppConfig.DEFAULT_VALUE_MERCHANT_EN_NAME);
    }

    public static String getSubAppName() {
        return SPTools.get(MerchantSetting.KEY_SUB_APP_NAME, AppConfig.DEFAULT_VALUE_SUB_APP_NAME);
    }
}
