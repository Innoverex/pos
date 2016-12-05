package cn.basewin.unionpay.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import cn.basewin.unionpay.view.AddAccountDialog;
import cn.basewin.unionpay.view.HintDialog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/24 11:48<br>
 * 描述: 拿到dialog的一个帮助类<br>
 */
public class DialogHelper {
    //这是一个添加柜员的dialog
    public static AddAccountDialog getAddAccountDialog(Context context) {
        return new AddAccountDialog(context);
    }

    //提示的dialog类
    public static HintDialog getHintDialog(Context context) {
        return new HintDialog(context);
    }

    public static void showAndClose(final Activity context, String msg) {
        if (context == null) {
            return;
        }
        if (context.isFinishing()) {
            return;
        }
        HintDialog d = getHintDialog(context);
        d.setTextHint(msg);
        d.setHasBtn_ok();
        d.setHintDialogListening(new HintDialog.HintDialogListening() {
            @Override
            public void ok() {
                context.finish();
            }

            @Override
            public void calcel() {
                context.finish();
            }
        });
        d.show();
    }

    public static void show(final Activity context, String msg) {
        HintDialog d = getHintDialog(context);
        d.setTextHint(msg);
        d.setHasBtn_ok();
        d.show();
    }

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "请选择手续费支付方式", arrays, onClickListener);
    }
}
