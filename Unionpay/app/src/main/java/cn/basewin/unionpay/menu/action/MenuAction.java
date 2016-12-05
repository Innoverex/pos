package cn.basewin.unionpay.menu.action;

import android.app.Activity;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 14:08<br>
 * 描述:  <br>
 */
public abstract class MenuAction {
    protected static final String TAG = MenuAction.class.getName();
    private int action;
    private String resName;
    private int resIcon;
    private Runnable run;
    private Activity context;

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public void setRun(Runnable run) {
        this.run = run;
    }


    public String getResName() {
        return "1111";
    }

    public int getResIcon() {
        return 1;
    }

    public Runnable getRun() {
        return null;
    }

    @Override
    public String toString() {
        return "MenuAction{" +
                "action=" + action +
                ", resName='" + resName + '\'' +
                ", resIcon=" + resIcon +
                ", run=" + run +
                ", context=" + context +
                '}';
    }
}
