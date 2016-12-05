package cn.basewin.unionpay.setting;

import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;
import cn.basewin.unionpay.view.HintDialog;

public class SetTmkValueActivity extends BaseSysSettingAty {

    private EditText et_tmk_id;
    private EditText et_tmk;
    private HintDialog hdialog;
    private static final int MSG_LOAD_KEY = 1000;
    private static final int MSG_LOAD_START = MSG_LOAD_KEY + 1;
    private static final int MSG_LOAD_OVER = MSG_LOAD_KEY + 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_KEY:
                    TLog.showToast("开始写入。。。");
                    String kv = et_tmk.getText().toString().trim();
                    boolean b = TradeEncUtil.loadMainKey(kv);
                    String info = "写入主密钥：";
                    if (b) {
                        info = info + "成功！";
                    } else {
                        info = info + "失败！";
                    }
                    Message m = handler.obtainMessage(MSG_LOAD_OVER);
                    m.obj = info;
                    m.sendToTarget();
                    break;
                case MSG_LOAD_START:
                    hdialog = getDialog("开始写入...");
                    hdialog.setNoBtn();
                    hdialog.show();
                    break;
                case MSG_LOAD_OVER:
                    if (null != hdialog && hdialog.isShowing()) {
                        hdialog.dismiss();
                    }
                    hdialog = getDialog((String) msg.obj);
                    hdialog.setHasBtn_ok();
                    hdialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                        @Override
                        public void ok() {
                            finish();
                        }

                        @Override
                        public void calcel() {
                        }
                    });
                    hdialog.show();
                    break;
            }
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_set_tmk_value;
    }

    @Override
    public String getAtyTitle() {
        return "手输密钥";
    }

    @Override
    public void afterSetContentView() {
        et_tmk_id = (EditText) findViewById(R.id.et_tmk_id);
        et_tmk = (EditText) findViewById(R.id.et_tmk);
    }

    @Override
    public void save() {
        String tmk = et_tmk.getText().toString().trim();
        if (null != tmk && tmk.length() == 32) {
            handler.sendEmptyMessage(MSG_LOAD_START);
            handler.sendEmptyMessageDelayed(MSG_LOAD_KEY, 1000);
        } else {
            TLog.showToast("请输入合法密钥！");
        }
    }

    private HintDialog getDialog(String msg) {
        HintDialog dialog = DialogHelper.getHintDialog(this);
        dialog.setTextHint(msg);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
