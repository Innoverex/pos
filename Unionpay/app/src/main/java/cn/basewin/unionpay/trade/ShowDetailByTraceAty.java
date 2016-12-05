package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;
import cn.basewin.unionpay.view.CustomInputDialog;

/**
 * 交易查询按照凭证号查询
 */
public class ShowDetailByTraceAty extends BaseShowDetailAty {

    private CustomInputDialog dialog;
    private static final String TAG = ShowDetailByTraceAty.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goneBtn();
        showInputDialog();
    }

    private void setView(TransactionData td) {
        String[] l = UIDataHelper.detail();
        String[] r = new String[5];
        setName(td.getName());
        r[0] = TDevice.hiddenCardNum(td.getPan());
        r[1] = td.getTrace();
        r[2] = td.getAuthCode();
        r[3] = PosUtil.changeAmout(td.getAmount());
        r[4] = TDevice.formatDateYDT(td.getYear() + td.getDate() + td.getTime());
        setData(l, r);
        excute();
    }


    private void showInputDialog() {
        dialog = new CustomInputDialog(ShowDetailByTraceAty.this, R.style.Dialog_Fullscreen_title);
        dialog.setRdLeftButton("取消", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.setRdRightButton("确定", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismissWithReturnResult();
            }
        });
        dialog.setResultWithDismiss(false);
        dialog.setRdMsg("请输入6位凭证号");
        dialog.setEditText(InputType.TYPE_NULL, new CustomInputDialog.RedialogInputResult() {
            @Override
            public void InputResult(String str) {
                Log.i(TAG, "用户输入的凭证号：" + str);
                selectTrace(str);
            }
        }, 6);
        dialog.setCheckLen(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void selectTrace(String trace) {
        trace = PosUtil.numToStr6(trace);
        WhereBuilder wb = WhereBuilder.b("trace", "=", trace);
        TransactionData td = null;
        try {
            td = TransactionDataDao.selectValid(wb);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (td == null) {
            TLog.showToast("未找到匹配的交易");
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        setView(td);
    }
}
