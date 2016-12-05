package cn.basewin.unionpay.setting;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.utils.TLog;


/**
 * Created by lhc on 2016/7/13.
 */
public abstract class BaseSysSettingAty extends BaseFlowAty {
    private TextView tv_cancel, tv_title, tv_save;
    public Context mContext;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        beforeSetContentView();
        setContentView(getContentView());
        initTile(getAtyTitle());
        afterSetContentView();
        setTimerFlag(false);
    }

    private final void initTile(String title) {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_save = (TextView) findViewById(R.id.tv_save);

        tv_title.setText(title);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 此方法在Ativity生命周期中OnCreate中调用<br>
     * SetContentView之前执行<br>
     * 如果有些方法必须在setContentView之前调用，可重写此方法
     */
    protected void beforeSetContentView() {

    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getContentView();

    /**
     * 获取系统设置标题
     *
     * @return
     */
    public abstract String getAtyTitle();

    /**
     * 此方法在Ativity生命周期中OnCreate中调用<br>
     * SetContentView之后执行<br>
     * 用于子类进行一些初始化操作
     */
    public abstract void afterSetContentView();

    /**
     * 保存设置
     */
    public abstract void save();

    public void showModifyHint() {
        TLog.showToast("修改" + getAtyTitle() + "成功");
    }

    /**
     * 设置右边按钮的文字
     *
     * @param txt
     */
    public void setRightBtnText(String txt) {
        if (tv_save != null) {
            tv_save.setText(txt);
        }
    }

    /**
     * 设置右侧按钮可见
     *
     * @param isVisible
     */
    public void setRightBtnVisible(boolean isVisible) {
        tv_save.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
