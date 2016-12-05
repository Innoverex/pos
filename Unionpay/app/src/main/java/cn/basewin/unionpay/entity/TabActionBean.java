package cn.basewin.unionpay.entity;

import android.os.Bundle;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/26 16:05<br>
 * 描述:  <br>
 */
public class TabActionBean {
    private int idx;
    private String resName;
    private int resIcon;
    private Class<?> clz;
    private Bundle bundle;

    public TabActionBean(int idx, String resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
        this.bundle = null;
    }

    public TabActionBean(int idx, String resName, int resIcon, Class<?> clz, Bundle bundle) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
