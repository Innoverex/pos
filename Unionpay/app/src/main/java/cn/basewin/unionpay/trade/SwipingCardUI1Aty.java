package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basewin.define.InputPBOCInitData;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePBOCAty;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 16:32<br>
 * 描述:   的刷卡界面 功能仅仅只有 控制界面显示 <br>
 */
public abstract class SwipingCardUI1Aty extends BasePBOCAty {
    protected ImageView iv_loading;//背景
    protected ProgressBar pb_loading;//背景
    protected ImageView iv_back;//背景
    protected ImageView iv_more;//背景
    protected TextView tv_swipe_tips;//刷卡提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);//背景
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);//等待
        iv_back = (ImageView) findViewById(R.id.iv_back);//关闭
        iv_more = (ImageView) findViewById(R.id.iv_more);//ic
        tv_swipe_tips = (TextView) findViewById(R.id.tv_swipe_tips);//刷卡提示

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clickBack();
            }
        });
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clickMore();
            }
        });
    }

    protected void _clickBack() {
        stopPBOC();
        finish();
    }

    //开启ic
    protected void _clickMore() {
        setRfFirst(false);
        stopPBOC();
        reStartPBOC();
    }

    //设置等待
    protected void setWait(boolean b) {
        if (b) {
            pb_loading.setVisibility(View.VISIBLE);
        } else {
            pb_loading.setVisibility(View.GONE);
        }
    }

    protected void setWait() {
        setWait(true);
    }

    protected void setSwipeCardTips(String tips) {
        tv_swipe_tips.setText(tips);
    }

    @Override
    protected void startPBOCEnd() {
        super.startPBOCEnd();
        changeBG();
        if (!getRfFirst()) {
            iv_more.setVisibility(View.GONE);
        }

//        switch (type_card) {
//            case InputPBOCInitData.USE_MAG_CARD:
//                iv_more.setVisibility(View.GONE);
//                return;
//            case InputPBOCInitData.USE_RF_CARD:
//                iv_more.setVisibility(View.GONE);
//                return;
//            case InputPBOCInitData.USE_IC_CARD:
//                iv_more.setVisibility(View.GONE);
//                return;
//        }
//        if (isRfFirst) {
//            iv_more.setVisibility(View.GONE);
//        }
    }


    protected void changeBG() {
        if (AppConfig.POS_MODEL.substring(0, 5).equals(AppConfig.POS_P2000)) {
            setP2000();
        } else if (AppConfig.POS_MODEL.substring(0, 5).equals(AppConfig.POS_P8000)) {
            setP8000();
        } else {
            setP2000();
        }
    }

    protected void setP2000() {
        if (TDevice.isZh()) {
            P2000ZH();
        } else {
            P2000EN();
        }
    }

    protected void setP8000() {
        if (TDevice.isZh()) {
            P8000ZH();
        } else {
            P8000EN();
        }
    }

    //中文
    protected void P2000ZH() {
        TLog.l("getType_card()" + getType_card());
        switch (getType_card()) {
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf_mag);
                break;
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf);
                break;
            case InputPBOCInitData.USE_MAG_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf);
                break;
            case InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic);
                break;
        }
    }

    //英文
    protected void P2000EN() {
        switch (getType_card()) {
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_ic_rf_mag);
                break;
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_rf_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_ic_rf);
                break;
            case InputPBOCInitData.USE_MAG_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_rf);
                break;
            case InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_usa_ic);
                break;
        }
    }

    //中文
    protected void P8000ZH() {
        switch (getType_card()) {
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf_mag);
                break;
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf);
                break;
            case InputPBOCInitData.USE_MAG_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf);
                break;
            case InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic);
                break;
        }
    }

    //英文
    protected void P8000EN() {
        int i = 0;
        switch (getType_card()) {
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf_mag);
                break;
            case InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic_rf);
                break;
            case InputPBOCInitData.USE_MAG_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_mag);
                break;
            case InputPBOCInitData.USE_RF_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_rf);
                break;
            case InputPBOCInitData.USE_IC_CARD:
                iv_loading.setImageResource(R.mipmap.p2000_en_ic);
                break;
        }
        if (i != 0) {
            iv_loading.setImageResource(i);
        }
    }
}
