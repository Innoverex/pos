package cn.basewin.unionpay.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.ui.adapter.KeyValueAdapter;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.UIDataHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/1 11:28<br>
 * 描述: map 界面 <br>
 */
public class KeyValueAty extends BaseFlowAty {
    protected ListView lv_key_value_data;
    protected Button btn_dialog_account_unok, btn_dialog_account_ok;
    protected LinearLayout ll_key_value_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_value);
        lv_key_value_data = (ListView) findViewById(R.id.lv_key_value_data);
        ll_key_value_btn = (LinearLayout) findViewById(R.id.ll_key_value_btn);

        btn_dialog_account_unok = (Button) findViewById(R.id.btn_dialog_account_unok);
        btn_dialog_account_ok = (Button) findViewById(R.id.btn_dialog_account_ok);

        btn_dialog_account_unok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUnOK();
            }
        });
        btn_dialog_account_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOK();
            }
        });
        initTitle();
    }

    protected void onClickOK() {
    }

    protected void onClickUnOK() {
    }

    protected void setAdapterData(String[] s) {
        setAdapterData(UIDataHelper.arrayToListMap(s));
    }

    protected void setAdapterData(List<Map> m) {
        if (m == null) {
            DialogHelper.showAndClose(this, "数据为空！");
            return;
        }
        KeyValueAdapter mAdapter = new KeyValueAdapter(this, m, R.layout.item_key_value);
        lv_key_value_data.setAdapter(mAdapter);
    }

    public void setBtnVisibility(int visibility) {
        ll_key_value_btn.setVisibility(visibility);
    }

    protected void setBtnVisibility(boolean ok, boolean unOk) {
        if (ok) {
            btn_dialog_account_ok.setVisibility(View.VISIBLE);
        } else {
            btn_dialog_account_ok.setVisibility(View.GONE);
        }
        if (unOk) {
            btn_dialog_account_unok.setVisibility(View.VISIBLE);
        } else {
            btn_dialog_account_unok.setVisibility(View.GONE);
        }
    }
}
