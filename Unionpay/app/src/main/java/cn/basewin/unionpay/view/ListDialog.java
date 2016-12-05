package cn.basewin.unionpay.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TDevice;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 17:22<br>
 * 描述：
 */
public class ListDialog extends Dialog {
    private String[] choice;
    private String choose;
    private Context mContext;
    private RadioGroup rg_list_radio;
    private OnRadioBtnCheckedChangedListener mListener;

    public ListDialog(Context context, String choose, String[] choice, OnRadioBtnCheckedChangedListener listener) {
        super(context);
        this.mContext = context;
        this.choice = choice;
        this.choose = choose;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_listradio);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (TDevice.getScreenWidth() * 0.8);
        getWindow().setAttributes(lp);

        rg_list_radio = (RadioGroup) findViewById(R.id.rg_list_radio);
        for (String s : choice) {
            RadioButton rb = new RadioButton(mContext);
            rb.setText(s);
            rb.setGravity(Gravity.CENTER);
            rb.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            rb.setTextSize(25);
            if (s.equals(choose)) {
                rb.setChecked(true);
            } else {
                rb.setChecked(false);
            }
            rg_list_radio.addView(rb);
        }

        rg_list_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                choose = radioButton.getText().toString();
                if (mListener != null) {
                    mListener.onCheckedRadioBtnChange(choose);
                }
                dismiss();
            }
        });
    }

    public interface OnRadioBtnCheckedChangedListener {
        void onCheckedRadioBtnChange(String str);
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }
}
