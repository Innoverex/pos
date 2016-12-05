package cn.basewin.unionpay.menu.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridView;

import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.menu.action.MenuAction;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/26 19:06<br>
 * 描述: grid 的界面<br>
 */
public abstract class GridAty extends Activity {
    private static final String TAG = GridAty.class.getName();
    private GridView gv_gridaty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gv_gridaty = (GridView) findViewById(R.id.gv_gridaty);

        new AsyncTask<Void, Void, List<MenuAction>>() {
            @Override
            protected List<MenuAction> doInBackground(Void... params) {
                List<MenuAction> gritData = getGritData();
                TLog.l("本次开启菜单 " + gritData.size() + "个属性");
                return gritData;
            }

            @Override
            protected void onPostExecute(List<MenuAction> menuActions) {
                GridItemAdapter gridItemAdapter = new GridItemAdapter(GridAty.this, menuActions, R.layout.item_grid);
                gridItemAdapter.setmActivity(GridAty.this);
                gv_gridaty.setAdapter(gridItemAdapter);
            }
        }.execute();
    }

    protected abstract List<MenuAction> getGritData();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK &&getIntent().getIntExtra("menuAction", 0)== ActionConstant.ACTION_SETTING_GROUP) {
//            UIHelper.SignInAty(this);
//            return false;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
