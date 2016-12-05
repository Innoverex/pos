package cn.basewin.unionpay.trade;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.base.CommonAdapter;
import cn.basewin.unionpay.base.ViewHolder;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/15 12:23<br>
 * 描述：选择页面的基类
 */
public abstract class BaseChooseAty extends BaseFlowAty {
    public Context mContext;
    public ListView lv_choose;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_choose);
        initTitle();
        this.mContext = this;
        lv_choose = (ListView) findViewById(R.id.lv_choose);
        tv_title.setText(getAtyTitle());
        lv_choose.setAdapter(new CommonAdapter<String>(mContext, getLists(), R.layout.item_base_choose) {
            @Override
            public void convert(ViewHolder holder, final String s) {
                LinearLayout ll_setting = holder.getView(R.id.ll_choose);
                final RadioButton rb_setting = holder.getView(R.id.rb_choose);
                rb_setting.setText(s);
                ll_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rb_setting.setChecked(true);
                        onItemClick(s);
                    }
                });
            }
        });
    }

    public abstract List<String> getLists();

    public abstract void onItemClick(String str);

    /**
     * 获取系统设置标题
     *
     * @return
     */
    public abstract String getAtyTitle();
}
