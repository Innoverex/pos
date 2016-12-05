package cn.basewin.unionpay.menu.ui;

import android.content.Intent;
import android.util.Log;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.menu.MenuHelper;
import cn.basewin.unionpay.menu.action.MenuAction;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/6 10:32<br>
 * 描述: 交易查询 <br>
 */
public class GridMenuAty extends GridAty {
    public static final String KEY_ACTION = "menuAction";
    protected int menuAction;
    private HintDialog hintDialog;
    private static final String TAG = GridMenuAty.class.getSimpleName();

    @Override
    protected List<MenuAction> getGritData() {
        menuAction = getIntent().getIntExtra(KEY_ACTION, 0);
        TLog.l("打开菜单id：" + menuAction);
        return MenuHelper.getMenuAction(menuAction, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TDevice.power_open();
        LedUtil.end();
        checkData();
    }

    protected void checkData() {
        if (ActionConstant.MENU_MAIN == menuAction) {
            Log.e(TAG, "启动主菜单！");
            TransactionDataDao.showDbAll();
            WhereBuilder wb = WhereBuilder.b("needPrint", "=", true);
            List<TransactionData> tds = null;
            try {
                tds = TransactionDataDao.select(wb, "id");
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (null != tds && tds.size() > 0) {
                hintDialog = getDialog(getString(R.string.has_print));
                hintDialog.setHasBtn_ok();
                hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        Intent intent = new Intent(GridMenuAty.this, PrintWaitAty.class);
                        intent.putExtra(PrintWaitAty.PRINT_STATE, PrintWaitAty.PRINT_AGAIN);
                        startActivity(intent);
                    }

                    @Override
                    public void calcel() {
                    }
                });
                hintDialog.show();
            }
        }
    }

    protected HintDialog getDialog(String msg) {
        hintDialog = DialogHelper.getHintDialog(this);
        hintDialog.setTextHint(msg);
        return hintDialog;
    }
}
