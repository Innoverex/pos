package cn.basewin.unionpay.trade;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.xutils.ex.DbException;

import java.io.File;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePrintImp;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.setting.TradeSettlementSettingAty;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.SettlementUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIHelper;
import cn.basewin.unionpay.view.CustomInputDialog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * Created by kxf on 2016/8/22.
 */
public abstract class BasePrintSettleWaitAty extends BaseWaitAty {
    protected static final String TAG = "BasePrintSettleWaitAty";
    protected Activity activity = this;
    protected HintDialog hintDialog;
    protected PrintClient demo;
    protected List<TransactionData> tds;

    protected BasePrintImp cpu = new BasePrintImp(activity) {
        @Override
        public void onPrintError(int code, String msg) {
            hintDialog = getDialog(msg + ",是否重新打印？");
            hintDialog.setHasBtn();
            hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                @Override
                public void ok() {
                    try {
                        demo.printNext();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void calcel() {
                    if (isPrintSettle()) {
                        deleteAllAndSigin();
                    }
                    finish();
                }
            });
            hintDialog.show();
        }

        @Override
        public void onStart() {
            super.onStart();
            start();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            onPrintFinish();
        }
    };

    /**
     * 打印结束后执行的方法
     */
    protected abstract void onPrintFinish();

    /**
     * 是否是打印结算单
     *
     * @return
     */
    protected abstract boolean isPrintSettle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)...");
        setTimerFlag(false);
        setHint(getString(R.string.printing));
    }

    protected HintDialog getDialog(String msg) {
        hintDialog = DialogHelper.getHintDialog(activity);
        hintDialog.setTextHint(msg);
        return hintDialog;
    }

    protected void deleteAllAndSigin() {
        try {
            TransactionDataDao.deleteAll();
            SettlementUtil.setCurrentSettleState(false);
        } catch (DbException e) {
            e.printStackTrace();
        }

        File f = new File(AppConfig.DEFAULT_SAVE_IMAGE_PATH);
        TLog.l("f.getPath()=" + f.getPath());
        deleteDir(f);
        TLog.e(TAG, "删除签名图片");

        SettingConstant.setBatchAuto();
        if (TradeSettlementSettingAty.getNeedAutoSignout()) {
            FlowControl flowControl = new FlowControl();
            flowControl.begin(NetWaitAty.class).start(this, ActionConstant.ACTION_SIGN_OUT);
        } else {
            UIHelper.menu(this, ActionConstant.MENU_MAIN);
        }
    }

    public void deleteDir(File d) {
        if (!d.exists()) {
            return;
        }
        if (d.isDirectory()) {
            File[] fs = d.listFiles();
            if (null != fs && fs.length > 0) {
                for (File f : fs) {
                    deleteDir(f);
                }
            }
        } else {
            d.delete();
        }
    }

    protected void showCuatomDialogPrintSuccess() {
        final CustomInputDialog dialog = new CustomInputDialog(activity, R.style.Dialog_Fullscreen_title);
        dialog.setRdMsg("打印完成！");
        dialog.setRdLeftButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteAllAndSigin();
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}