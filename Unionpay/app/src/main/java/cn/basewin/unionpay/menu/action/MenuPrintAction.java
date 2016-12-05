package cn.basewin.unionpay.menu.action;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.view.HintDialog;

/**
 * Created by kxf on 2016/9/2.
 * 打印的类专用
 */
public abstract class MenuPrintAction extends MenuAction {
    protected PrintClient demo;
    protected Handler handler;
    public HintDialog dialog;
    public static final int MSG_SHOW_DIALOG = 1000;
    public static final int MSG_DISMISS_DIALOG = MSG_SHOW_DIALOG + 1;
    public static final int MSG_PRINT_NEXT = MSG_SHOW_DIALOG + 2;
    public static final int MSG_PRINT_NEXT_AND_SHOW_DIALOD = MSG_SHOW_DIALOG + 3;

    public final Runnable getRun() {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case MSG_SHOW_DIALOG:
                                dialog = getDialog("打印中...");
                                dialog.setNoBtn();
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                handler.removeMessages(MSG_DISMISS_DIALOG);
                                handler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, AppConfig.DISMISS_DIALOG_TIMEOUT * 2 * 1000);
                                break;
                            case MSG_DISMISS_DIALOG:
                                Log.i(TAG, "MSG_DISMISS_DIALOG...");
                                if (null != dialog && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                break;
                            case MSG_PRINT_NEXT:
                                try {
                                    demo.printNext();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case MSG_PRINT_NEXT_AND_SHOW_DIALOD:
                                handler.sendEmptyMessage(MSG_SHOW_DIALOG);
                                handler.sendEmptyMessageDelayed(MSG_PRINT_NEXT, 500);
                                break;
                        }
                    }
                };
            }
        });
        return getRunChild();
    }

    public abstract Runnable getRunChild();

    public HintDialog getDialog(String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = DialogHelper.getHintDialog(getContext());
        dialog.setTextHint(msg);
        return dialog;
    }
}
