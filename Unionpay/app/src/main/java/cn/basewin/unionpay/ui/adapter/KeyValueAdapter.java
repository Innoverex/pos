package cn.basewin.unionpay.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.CommonAdapter;
import cn.basewin.unionpay.base.ViewHolder;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/1 11:12<br>
 * 描述: key value list <br>
 */
public class KeyValueAdapter extends CommonAdapter<Map> {
    public KeyValueAdapter(Context context, List<Map> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    private TextView tv_key_value_key, tv_key_value_value;
    private LinearLayout ll_item_key_value;

    @Override
    public void convert(ViewHolder holder, Map map) {
        String key = (String) map.get("key");
        String value = (String) map.get("value");
        tv_key_value_key = holder.getView(R.id.tv_key_value_key);
        tv_key_value_value = holder.getView(R.id.tv_key_value_value);
        ll_item_key_value = holder.getView(R.id.ll_item_key_value);
        if (postion % 2 != 0) {
            ll_item_key_value.setBackgroundResource(R.color.list_select);
        } else {
            ll_item_key_value.setBackgroundResource(R.color.white);
        }
        key = key == null ? "" : key;
        value = value == null ? "" : value;
        tv_key_value_key.setText(key);
        tv_key_value_value.setText(value);
    }
}
