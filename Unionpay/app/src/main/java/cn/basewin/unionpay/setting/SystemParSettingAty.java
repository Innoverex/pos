package cn.basewin.unionpay.setting;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.ListDialog;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/13 16:34<br>
 * 描述：系统参数设置
 */
public class SystemParSettingAty extends BaseSysSettingAty {
    private static final String TAG = SystemParSettingAty.class.getName();
    /**
     * 流水号
     */
    private static final String KEY_TRACE = TAG + "trace";
    /**
     * 批次号
     */
    private static final String KEY_BATCH = TAG + "batch";
    /**
     * 是否打印中文收单行
     */
    private static final String KEY_NEED_PRINT_ACQUIRER_NAME = TAG + "print_acquirer_name";
    /**
     * 是否打印中文发卡行
     */
    private static final String KEY_NEED_PRINT_ISSUER_NAME = TAG + "print_issuer_name";
    /**
     * 套打签购单样式
     */
    private static final String KEY_SALES_SLIP_TYPE = TAG + "sales_slip_type";
    /**
     * 热敏打印联数
     */
    private static final String KEY_PRINT_NUMBER = TAG + "print_number";
    /**
     * 签购单是否打印英文
     */
    private static final String KEY_SLIP_HAS_ENGLISH = TAG + "slip_has_english";
    /**
     * 冲正重发次数
     */
    private static final String KEY_REVERSE_TIMES = TAG + "reverse_times";
    /**
     * 最大交易笔数
     */
    private static final String KEY_MAX_TRADE_NUMBER = TAG + "max_trade_number";
    /**
     * 内外置密码键盘
     */
    private static final String KEY_INNER_PINPAD = TAG + "inner_pinpad";
    /**
     * 小费比例
     */
    private static final String KEY_FEE_RATE = TAG + "fee_rate";
    /**
     * 撤销/退货类交易金额是否打印负号
     */
    private static final String KEY_PRINT_VOID_MINUS = TAG + "print_void_minus";
    /**
     * 签到重发次数
     */
    private static final String KEY_SIGN_TIMES = TAG + "sign_times";
    /**
     * 拨号重发次数
     */
    private static final String KEY_DIAL_TIMES = TAG + "dial_times";
    /**
     * 流水号
     */
    private EditText et_trace;
    /**
     * 批次号
     */
    private EditText et_batch;
    /**
     * 热敏打印联数
     */
    private EditText et_print_number;
    /**
     * 重正重发次数
     */
    private EditText et_reverse_times;
    /**
     * 最大交易笔数
     */
    private EditText et_max_trade_number;
    /**
     * 内外置密码键盘
     */
    private TextView tv_inner_pinpad;
    /**
     * 小费比例
     */
    private EditText et_fee_rate;
    /**
     * 签到重发次数
     */
    private EditText et_sign_times;
    /**
     * 拨号重试次数
     */
    private EditText et_dial_times;
    /**
     * 是否打印系统收单行
     */
    private ToggleButton togbtn_print_acquirer_name;
    /**
     * 是否打印中文发卡行
     */
    private ToggleButton togbtn_print_issuer_name;
    /**
     * 套打签购单样式
     */
    private TextView tv_sales_slip_type;
    /**
     * 签购单是否打印英文
     */
    private ToggleButton togbtn_slip_has_english;
    /**
     * 撤销/退货类交易金额是否打印负号
     */
    private ToggleButton togbtn_print_void_minus;
    private String[] sales_slip_type = {"新签购单", "旧签购单", "空白签购单"};
    private String[] pinpad_inner = {"内置密码键盘", "外置密码键盘"};

    private void initViews() {
        et_trace = (EditText) findViewById(R.id.et_liushuihao);
        et_batch = (EditText) findViewById(R.id.et_picihao);
        et_print_number = (EditText) findViewById(R.id.et_remindayinlianshu);
        et_reverse_times = (EditText) findViewById(R.id.et_chongzhengchongfacishu);
//        et_max_trade_number = (EditText) findViewById(R.id.et_zuidajiaoyibishu);
        et_fee_rate = (EditText) findViewById(R.id.et_xiaofeibili);
        et_sign_times = (EditText) findViewById(R.id.et_qiandaochongfacishu);
//        et_dial_times = (EditText) findViewById(R.id.et_bohaochongshicishu);
        togbtn_print_acquirer_name = (ToggleButton) findViewById(R.id.togbtn_shifoudayinzhongwenshoudanhang);
        togbtn_print_issuer_name = (ToggleButton) findViewById(R.id.togbtn_shifoudayinzhongwenfakahang);
        togbtn_slip_has_english = (ToggleButton) findViewById(R.id.togbtn_qiangoudanshifoudayinyingwen);
        togbtn_print_void_minus = (ToggleButton) findViewById(R.id.togbtn_chexiaotuihuidayinfuhao);
//        tv_sales_slip_type = (TextView) findViewById(R.id.tv_syssetting_txt);
//        tv_inner_pinpad = (TextView) findViewById(R.id.tv_neiwaizhimimajianpan);
    }

    private void initData() {
        et_trace.setText(getTrace());
        et_batch.setText(getBatch());
        togbtn_print_acquirer_name.setChecked(getNeedPrintAcquirerName());
        togbtn_print_issuer_name.setChecked(getNeedPrintIssuerName());
        et_print_number.setText(getPrintNumber());
        togbtn_slip_has_english.setChecked(getSlipHasEnglish());
        et_reverse_times.setText(getReverseTimes());
//        et_max_trade_number.setText(SPTools.get(SystemParSettingAty.KEY_MAX_TRADE_NUMBER, "500"));
        et_fee_rate.setText(getFeeRate());
        togbtn_print_void_minus.setChecked(getPrintVoidMinus());
        et_sign_times.setText(getSignTimes());
//        et_dial_times.setText(SPTools.get(SystemParSettingAty.KEY_DIAL_TIMES, "3"));
//        tv_sales_slip_type.setText(SPTools.get(SystemParSettingAty.KEY_SALES_SLIP_TYPE, sales_slip_type[0]));
//        tv_inner_pinpad.setText(SPTools.get(SystemParSettingAty.KEY_INNER_PINPAD, pinpad_inner[0]));
    }

    public static String getTrace() {
        return SPTools.get(SystemParSettingAty.KEY_TRACE, AppConfig.DEFAULT_VALUE_TRACE);
    }

    public static String getBatch() {
        return SPTools.get(SystemParSettingAty.KEY_BATCH, AppConfig.DEFAULT_VALUE_BATCH);
    }

    public static void setTrace(String no) {
        SPTools.set(SystemParSettingAty.KEY_TRACE, no);
    }

    public static void setBatch(String no) {
        SPTools.set(SystemParSettingAty.KEY_BATCH, no);
    }

    public static boolean getNeedPrintAcquirerName() {
        return SPTools.get(SystemParSettingAty.KEY_NEED_PRINT_ACQUIRER_NAME, AppConfig.DEFAULT_VALUE_NEED_PRINT_ACQUIRER_NAME);
    }

    public static boolean getNeedPrintIssuerName() {
        return SPTools.get(SystemParSettingAty.KEY_NEED_PRINT_ISSUER_NAME, AppConfig.DEFAULT_VALUE_NEED_PRINT_ISSUER_NAME);
    }

    public static String getSalesSlipType() {
        return SPTools.get(SystemParSettingAty.KEY_SALES_SLIP_TYPE, AppConfig.DEFAULT_VALUE_SALES_SLIP_TYPE);
    }

    public static String getPrintNumber() {
        return SPTools.get(SystemParSettingAty.KEY_PRINT_NUMBER, AppConfig.DEFAULT_VALUE_PRINT_NUMBER);
    }

    public static boolean getSlipHasEnglish() {
        return SPTools.get(SystemParSettingAty.KEY_SLIP_HAS_ENGLISH, AppConfig.DEFAULT_VALUE_SLIP_HAS_ENGLISH);
    }

    public static String getReverseTimes() {
        return SPTools.get(SystemParSettingAty.KEY_REVERSE_TIMES, AppConfig.DEFAULT_VALUE_REVERSE_TIMES);
    }

    public static String getMaxTradeNumber() {
        return SPTools.get(SystemParSettingAty.KEY_MAX_TRADE_NUMBER, AppConfig.DEFAULT_VALUE_MAX_TRADE_NUMBER);
    }

    public static String getInnerPinpad() {
        return SPTools.get(SystemParSettingAty.KEY_INNER_PINPAD, AppConfig.DEFAULT_VALUE_INNER_PINPAD);
    }

    public static String getFeeRate() {
        return SPTools.get(SystemParSettingAty.KEY_FEE_RATE, AppConfig.DEFAULT_VALUE_FEE_RATE);
    }

    public static boolean getPrintVoidMinus() {
        return SPTools.get(SystemParSettingAty.KEY_PRINT_VOID_MINUS, AppConfig.DEFAULT_VALUE_PRINT_VOID_MINUS);
    }

    public static String getSignTimes() {
        return SPTools.get(SystemParSettingAty.KEY_SIGN_TIMES, AppConfig.DEFAULT_VALUE_SIGN_TIMES);
    }

    public static String getDialTimes() {
        return SPTools.get(SystemParSettingAty.KEY_DIAL_TIMES, AppConfig.DEFAULT_VALUE_DIAL_TIMES);
    }

    private void setListener() {
        tv_sales_slip_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choose = tv_sales_slip_type.getText().toString();
                Dialog dialog = new ListDialog(mContext, choose, sales_slip_type
                        , new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_sales_slip_type.setText(str);
                    }
                });
                dialog.show();
            }
        });

        tv_inner_pinpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choose = tv_inner_pinpad.getText().toString();
                Dialog dialog = new ListDialog(mContext, choose, pinpad_inner
                        , new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_inner_pinpad.setText(str);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_xitongcanshu;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.xitongcanshushezhi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
//        setListener();
    }

    @Override
    public void save() {
        String batch = et_batch.getText().toString();//批次号
        if (batch.length() != 6) {
            Toast.makeText(mContext, "请输入6位批次号", Toast.LENGTH_SHORT).show();
            return;
        }

        SPTools.set(SystemParSettingAty.KEY_TRACE, et_trace.getText().toString());
        SPTools.set(SystemParSettingAty.KEY_BATCH, et_batch.getText().toString());
        SPTools.set(SystemParSettingAty.KEY_NEED_PRINT_ACQUIRER_NAME, togbtn_print_acquirer_name.isChecked());
        SPTools.set(SystemParSettingAty.KEY_NEED_PRINT_ISSUER_NAME, togbtn_print_issuer_name.isChecked());
        SPTools.set(SystemParSettingAty.KEY_PRINT_NUMBER, et_print_number.getText().toString());
        SPTools.set(SystemParSettingAty.KEY_SLIP_HAS_ENGLISH, togbtn_slip_has_english.isChecked());
        SPTools.set(SystemParSettingAty.KEY_REVERSE_TIMES, et_reverse_times.getText().toString());
//        SPTools.set(SystemParSettingAty.KEY_MAX_TRADE_NUMBER, et_max_trade_number.getText().toString());
        SPTools.set(SystemParSettingAty.KEY_FEE_RATE, et_fee_rate.getText().toString());
        SPTools.set(SystemParSettingAty.KEY_PRINT_VOID_MINUS, togbtn_print_void_minus.isChecked());
        SPTools.set(SystemParSettingAty.KEY_SIGN_TIMES, et_sign_times.getText().toString());
//        SPTools.set(SystemParSettingAty.KEY_DIAL_TIMES, et_dial_times.getText().toString());
//        SPTools.set(SystemParSettingAty.KEY_INNER_PINPAD, tv_inner_pinpad.getText().toString());
//        SPTools.set(SystemParSettingAty.KEY_SALES_SLIP_TYPE, tv_sales_slip_type.getText().toString());
        showModifyHint();
    }
}
