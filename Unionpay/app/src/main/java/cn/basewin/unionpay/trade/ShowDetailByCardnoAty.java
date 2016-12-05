package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;
import cn.basewin.unionpay.view.CustomInputDialog;

/**
 * 交易查询-按照卡号查询
 */
public class ShowDetailByCardnoAty extends BaseShowDetailAty {
    private static final String TAG = ShowDetailByCardnoAty.class.getName();
    private List<TransactionData> trans;
    private CustomInputDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInputDialog();
    }

    private void initData(String cardNo) {
        try {
            WhereBuilder wb = WhereBuilder.b("pan", "like", "%" + cardNo + "%");
            trans = TransactionDataDao.selectValid(wb, "id");
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (trans == null || trans.size() < 1) {
            TLog.showToast("未找到匹配的交易");
        } else {
            Log.i(TAG, "trans[" + trans.size() + "]=" + trans);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (trans.size() < 2) {
                goneBtn();
            } else {
                visibleBtn();
            }
            setView(trans);
        }
    }

    private void setView(List<TransactionData> tra) {
        String[] l = UIDataHelper.detail();
        String[] r = new String[5];
        for (TransactionData td : tra) {
            setName(td.getName());
            r[0] = TDevice.hiddenCardNum(td.getPan());
            r[1] = td.getTrace();
            r[2] = td.getAuthCode();
            r[3] = PosUtil.changeAmout(td.getAmount());
            r[4] = TDevice.formatDateYDT(td.getYear() + td.getDate() + td.getTime());
            setData(l, r);
        }
        excute();
    }


    private void showInputDialog() {
        dialog = new CustomInputDialog(ShowDetailByCardnoAty.this, R.style.Dialog_Fullscreen_title);
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
        dialog.setRdMsg("请输入卡号");
        dialog.setEditText(InputType.TYPE_NULL, new CustomInputDialog.RedialogInputResult() {
            @Override
            public void InputResult(String str) {
                Log.i(TAG, "用户输入的卡号：" + str);
                initData(str);
            }
        }, 20);
        dialog.setCheckLen(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
