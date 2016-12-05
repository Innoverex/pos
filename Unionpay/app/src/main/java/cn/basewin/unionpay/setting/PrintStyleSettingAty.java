package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.ListDialog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/29 10:44<br>
 * 描述：签购单打印样式
 */
public class PrintStyleSettingAty extends BaseSysSettingAty {
    /**
     * 服务热线
     */
    private static final String KEY_SERVICE_HOTLINE = "PrintStyleSettingAty_service_hotline";
    /**
     * 签购单字体选择
     */
    private static final String KEY_FONT_TYPE = "PrintStyleSettingAty_font_type";
    /**
     * 未知发卡行打印名
     */
    private static final String KEY_UNKONW_ISSUE_PRINT_NAME = "PrintStyleSettingAty_unkonw_issue_bank_print_name";
    /**
     * 交易明细打印模式
     */
    private static final String KEY_TRANSACTION_DETAIL_STYLE = "PrintStyleSettingAty_transaction_detail_style";
    private EditText et_service_hotline;
    private TextView tv_font_type;
    private EditText et_unkonw_issue_bank_print_name;
    private TextView tv_transaction_detail_style;
    private String[] font_types = {"小字体", "中字体", "大字体"};
    private String[] transaction_details = {"1行", "30行"};

    private void initViews() {
        et_service_hotline = (EditText) findViewById(R.id.et_service_hotline);
        tv_font_type = (TextView) findViewById(R.id.tv_font_type);
        et_unkonw_issue_bank_print_name = (EditText) findViewById(R.id.et_unkonw_issue_bank_print_name);
        tv_transaction_detail_style = (TextView) findViewById(R.id.tv_transaction_detail_style);
    }

    private void initData() {
        et_service_hotline.setText(getServiceHotline());
        tv_font_type.setText(getPrintFont());
        et_unkonw_issue_bank_print_name.setText(getDefaultIss());
        tv_transaction_detail_style.setText(getDetailModel());
    }

    //交易明细打印模式
    public static String getDetailModel() {
        return SPTools.get(PrintStyleSettingAty.KEY_TRANSACTION_DETAIL_STYLE, AppConfig.DEFAULT_VALUE_TRANSACTION_DETAIL_STYLE);
    }

    //获取 默认 未知收单行发卡行打印名
    public static String getDefaultIss() {
        return SPTools.get(PrintStyleSettingAty.KEY_UNKONW_ISSUE_PRINT_NAME, AppConfig.DEFAULT_VALUE_UNKONW_ISSUE_PRINT_NAME);
    }

    //获得打印字体   大字体，中字体 小字体
    public static String getPrintFont() {
        return SPTools.get(PrintStyleSettingAty.KEY_FONT_TYPE, AppConfig.DEFAULT_VALUE_FONT_TYPE);
    }

    //获得热线服务电话
    public static String getServiceHotline() {
        return SPTools.get(PrintStyleSettingAty.KEY_SERVICE_HOTLINE, AppConfig.DEFAULT_VALUE_SERVICE_HOTLINE);
    }

    private void setListener() {
        tv_font_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialog dialog = new ListDialog(mContext, tv_font_type.getText().toString(),
                        font_types, new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_font_type.setText(str);
                    }
                });
                dialog.show();
            }
        });

        tv_transaction_detail_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialog dialog = new ListDialog(mContext, tv_transaction_detail_style.getText().toString(),
                        transaction_details, new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_transaction_detail_style.setText(str);
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_printstyle;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.print_style);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
        setListener();
    }

    @Override
    public void save() {

    }
}
