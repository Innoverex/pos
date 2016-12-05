package cn.basewin.unionpay.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 10:28<br>
 * 描述：
 */
public abstract class BaseSysFragment extends Fragment {
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(getContentView(), null);
        afterGetView(view);
        return view;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getContentView();

    /**
     * 此方法在Fragment生命周期中onCreateView中调用<br>
     * return view之前执行<br>
     * 用于子类进行一些初始化操作
     */
    public abstract void afterGetView(View view);

    /**
     * 保存设置
     */
    public abstract void save();
}
