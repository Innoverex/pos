package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.ui.adapter.KeyValueAdapter;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/16 18:07<br>
 * 描述: 数据显示 <br>
 * 子类请执行setData(String[] s1, String[] s2) 函数excute()
 */
public class BaseShowDetailAty extends BaseFlowAty {
    private TextView tv_name, tv_index;
    private Button btn_first, btn_back, btn_next, btn_last;
    private ListView lv_base_detail;
    private LinearLayout ll_btn;
    private List<List<Map>> _Map;
    private List<String> title = new ArrayList<>();
    private int local = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_detail);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_index = (TextView) findViewById(R.id.tv_index);

        ll_btn = (LinearLayout) findViewById(R.id.ll_btn);
        btn_first = (Button) findViewById(R.id.btn_first);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_last = (Button) findViewById(R.id.btn_last);

        lv_base_detail = (ListView) findViewById(R.id.lv_base_detail);

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFirst();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLast();
            }
        });

    }

    public void goneBtn() {
        ll_btn.setVisibility(View.GONE);
    }

    public void visibleBtn() {
        ll_btn.setVisibility(View.VISIBLE);
    }

    protected void clickFirst() {
        if (getMap().size() <= 0) {
            TLog.showToast("已经没有交易了！");
        } else {
            local = 1;
            excute();
        }
    }

    protected void clickBack() {
        local--;
        if (local < 1 || getMap().size() <= 0) {
            local++;
            TLog.showToast("已经没有交易了！");
        } else {
            excute();
        }
    }

    protected void clickNext() {
        local++;
        if (local > getMap().size()) {
            local--;
            TLog.showToast("已经没有交易了！");
        } else {
            excute();
        }
    }

    protected void clickLast() {
        if (getMap().size() <= 0) {
            TLog.showToast("已经没有交易了！");
        } else {
            local = getMap().size();
            excute();
        }
    }

    protected void setAdapterData(List<Map> m) {
        KeyValueAdapter mAdapter = new KeyValueAdapter(this, m, R.layout.item_key_value);
        lv_base_detail.setAdapter(mAdapter);
    }

    /**
     * 子类请 在数据集循环调用这个方法
     *
     * @param s1 key
     * @param s2 value
     */
    protected void setData(String[] s1, String[] s2) {
        List<List<Map>> map = getMap();
        List<Map> maps = UIDataHelper.setListMap_Vaule(s1, s2);
        map.add(maps);
    }

    /**
     * 设置头数据 也是在循环  中调用
     *
     * @param s
     */
    protected void setName(String s) {
        title.add(s);
    }

    protected List<List<Map>> getMap() {
        if (_Map == null || _Map.size() <= 0) {
            List<List<Map>> mapss = new ArrayList<>();
            _Map = mapss;
        }
        return _Map;
    }

    /**
     * 循环数据 设置完成后  执行此方法可以显示
     */
    protected void excute() {
        int i = local - 1;
        List<Map> maps = getMap().get(i);
        if (maps == null || maps.size() <= 0) {
            initUI();
        }
        try {
            tv_name.setText(title.get(i));
        } catch (Exception e) {
            tv_name.setText("");
        }
        tv_index.setText("第 " + (i + 1) + "/" + getMap().size() + " 条");
        setAdapterData(maps);
    }

    //初始化界面
    protected void initUI() {
        tv_name.setText("");
        tv_index.setText("");
        setAdapterData(null);
    }

}
