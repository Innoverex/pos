package cn.basewin.unionpay.setting;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.view.ListDialog;

public class SetDesActivity extends BaseSysSettingAty {


    private LinearLayout ll_des;
    private TextView tv_des;
    private ListDialog ld;
    private String[] cp;
    private static final String TAG = "SetDesActivity";
    private String desStr;

    @Override
    public int getContentView() {
        return R.layout.activity_set_des;
    }

    @Override
    public String getAtyTitle() {
        return "设置DES算法";
    }

    @Override
    public void afterSetContentView() {
        cp = new String[]{getString(R.string.danbeichang), getString(R.string.shuangbeichang)};
        ll_des = (LinearLayout) findViewById(R.id.ll_des);
        tv_des = (TextView) findViewById(R.id.tv_des);
        desStr = cp[1];
        ll_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ld = new ListDialog(mContext, desStr, cp, new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        desStr = str;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_des.setText(desStr);
                            }
                        });
                        Log.i(TAG, "str=" + str);
                    }
                });
                ld.show();
            }
        });
    }

    @Override
    public void save() {

    }
}
