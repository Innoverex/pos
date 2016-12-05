package cn.basewin.unionpay.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.CommonAdapter;
import cn.basewin.unionpay.base.ViewHolder;
import cn.basewin.unionpay.entity.TabActionBean;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/15 14:35<br>
 * 描述：
 */
public abstract class BaseListAty extends Activity {
    private ListView lv_setting;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syssetting_baselist);
        lv_setting = (ListView) findViewById(R.id.lv_setting);
        lv_setting.setAdapter(new CommonAdapter<TabActionBean>(this, getListData(), R.layout.item_baselist) {
            @Override
            public void convert(ViewHolder holder, final TabActionBean tabActionBean) {
                ImageView imageView = holder.getView(R.id.img_setting);
                TextView textView = holder.getView(R.id.tv_setting);
                LinearLayout linearLayout = holder.getView(R.id.ll_setting);
                textView.setText(tabActionBean.getResName());
                imageView.setImageResource(tabActionBean.getResIcon());
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tabActionBean.getClz() != null) {
                            Intent intent = new Intent(mContext, tabActionBean.getClz());
                            intent.putExtra(AppConfig.KEY_REQUEST_CODE, tabActionBean.getIdx());
                            if (tabActionBean.getBundle() != null) {
                                intent.putExtras(tabActionBean.getBundle());
                            }
                            BaseListAty.this.startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    protected abstract List<TabActionBean> getListData();
}
