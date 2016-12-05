package cn.basewin.unionpay.menu.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.CommonAdapter;
import cn.basewin.unionpay.base.ViewHolder;
import cn.basewin.unionpay.menu.action.MenuAction;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/26 15:33<br>
 * 描述:  <br>
 */
public class GridItemAdapter extends CommonAdapter<MenuAction> {
    public GridItemAdapter(Context context, List<MenuAction> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    private TextView tv_item_grid;
    private ImageView iv_item_grid;
    private LinearLayout ll_item_grid;
    private Activity mActivity;

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void convert(ViewHolder holder, final MenuAction menuAction) {
        iv_item_grid = holder.getView(R.id.iv_item_grid);
        tv_item_grid = holder.getView(R.id.tv_item_grid);
        ll_item_grid = holder.getView(R.id.ll_item_grid);

        iv_item_grid.setImageResource(menuAction.getResIcon());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iv_item_grid.getLayoutParams());
        layoutParams.height = layoutParams.width;
        iv_item_grid.setLayoutParams(layoutParams);
        iv_item_grid.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        tv_item_grid.setText(menuAction.getResName());

        ll_item_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuAction.getRun() != null) {
                    TLog.l("有run111");
                    mActivity.runOnUiThread(menuAction.getRun());
                } else {
                    TLog.l("没有run111");
                    DialogHelper.show(mActivity, "‘(*>﹏<*)′ ~正在努力开发中。");
                }
            }
        });
    }

    private OnAdapterItemClick listen;

    public void setListen(OnAdapterItemClick listen) {
        this.listen = listen;
    }

    public interface OnAdapterItemClick {
        void onAdapterClick(MenuAction bean);
    }
}
