package cn.basewin.unionpay.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basewin.services.CardBinder;
import com.basewin.services.ServiceManager;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.ApduErrCode;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.CustomInputDialog;

public class LoadTmkSwipeCardActivity extends BaseSysSettingAty {
    private View include_title;
    private TextView tv_swipe_tips;
    private ImageView iv_back;
    private RelativeLayout rl_swipe;
    private RelativeLayout rl_wait;
    private View v_wait;
    private TextView tv_wait;
    private Activity activity = this;
    private CustomInputDialog dialogMoveCard;

    public static final int MES_ICC_CARD = 127;
    public static final int MES_ICC_CARD_INSERT_MY = MES_ICC_CARD + 1;
    public static final int MES_ICC_CARD_MOVE_MY = MES_ICC_CARD + 2;
    public static final int MES_ICC_CARD_INSERT_WH = MES_ICC_CARD + 3;
    public static final int MES_ICC_CARD_MOVE_WH = MES_ICC_CARD + 4;
    public static final int MES_ICC_DISMISS_DIALOG = MES_ICC_CARD + 5;
    public static final int MES_ICC_CARD_START_SWIPE_MY = MES_ICC_CARD + 6;
    public static final int MES_ICC_CARD_START_SWIPE_WH = MES_ICC_CARD + 7;
    public static final int MES_ICC_CARD_INPUT_INDEX = MES_ICC_CARD + 8;
    public static final int MES_VIEW_START = MES_ICC_CARD + 9;
    public static final int MES_VIEW_STOP = MES_ICC_CARD + 10;
    public static final int MES_VIEW_SET_HINT = MES_ICC_CARD + 11;
    public static final int MES_ICC_CARD_CHECK_PIN_MY = MES_ICC_CARD + 12;
    public static final int MES_ICC_CARD_CHECK_PIN_WH = MES_ICC_CARD + 13;


    public static final String TAG_DOWNLOAD_TMK_TYPE = "tag_download_tmk_type";
    public static final int MES_DOWNLOAD_TMK_BY_IC = MES_ICC_CARD + 101;
    public static final int MES_DOWNLOAD_TMK_BY_HAND = MES_ICC_CARD + 102;

    private boolean isAlive = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MES_ICC_CARD_START_SWIPE_MY:
                    tv_swipe_tips.setText("请使用你的密钥卡");
                    isInsertIcc(MES_ICC_CARD_INSERT_MY, true);
                    break;
                case MES_ICC_CARD_START_SWIPE_WH:
                    tv_swipe_tips.setText("请使用你的维护卡");
                    isInsertIcc(MES_ICC_CARD_INSERT_WH, true);
                    break;
                case MES_ICC_CARD_INSERT_MY:
                    rl_swipe.setVisibility(View.GONE);
                    rl_wait.setVisibility(View.VISIBLE);
                    final CustomInputDialog dialogCustom = new CustomInputDialog(activity, R.style.Dialog_Fullscreen_title);
                    dialogCustom.setRdLeftButton("取消", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialogCustom.dismiss();
                            finish();
                        }
                    });
                    dialogCustom.setRdRightButton("确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialogCustom.dismissWithReturnResult();
                        }
                    });
                    dialogCustom.setRdMsg("请输入密钥卡密码");
                    dialogCustom.setEditText(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD, new CustomInputDialog.RedialogInputResult() {
                        @Override
                        public void InputResult(String str) {
                            Log.i(TAG, "用户输入的密钥卡密码：" + str);
                            start();
                            Message msg = new Message();
                            msg.what = MES_ICC_CARD_CHECK_PIN_MY;
                            msg.obj = str;
                            handler.sendMessageDelayed(msg, 500);
                        }
                    }, 6);
                    dialogCustom.setFinishIfDismiss(activity);
                    dialogCustom.setCanceledOnTouchOutside(false);
                    dialogCustom.show();
                    break;
                case MES_ICC_CARD_INSERT_WH:
                    rl_swipe.setVisibility(View.GONE);
                    rl_wait.setVisibility(View.VISIBLE);
                    final CustomInputDialog dialog1 = new CustomInputDialog(activity, R.style.Dialog_Fullscreen_title);
                    dialog1.setRdLeftButton("取消", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog1.dismiss();
                            finish();
                        }
                    });
                    dialog1.setRdRightButton("确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog1.dismissWithReturnResult();
                        }
                    });
                    dialog1.setRdMsg("请输入维护卡密码");
                    dialog1.setEditText(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD, new CustomInputDialog.RedialogInputResult() {
                        @Override
                        public void InputResult(String str) {
                            Log.i(TAG, "用户输入的维护卡密码：" + str);
                            start();
                            Message msg = new Message();
                            msg.what = MES_ICC_CARD_CHECK_PIN_WH;
                            msg.obj = str;
                            handler.sendMessageDelayed(msg, 500);
                        }
                    }, 6);
                    dialog1.setFinishIfDismiss(activity);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    break;
                case MES_ICC_CARD_INPUT_INDEX:
                    final CustomInputDialog dialog = new CustomInputDialog(activity, R.style.Dialog_Fullscreen_title);
                    dialog.setRdLeftButton("取消", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.setRdRightButton("确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog.dismissWithReturnResult();
                        }
                    });
                    dialog.setRdMsg("请输入密钥索引号");
                    dialog.setEditText(InputType.TYPE_NULL, new CustomInputDialog.RedialogInputResult() {
                        @Override
                        public void InputResult(String str) {
                            Log.i(TAG, "用户输入的密钥卡索引号：" + str);
                            int ret = IccPersonal.selectTMK(str);
                            if (ret == 0) {
                                show("查找密钥成功");
                                handler.sendEmptyMessage(MES_ICC_CARD_MOVE_MY);
                            } else {
                                showDialog(ApduErrCode.ProcErr(ret));
                            }
                        }
                    }, 11);
                    dialog.setFinishIfDismiss(activity);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    break;
                case MES_ICC_CARD_MOVE_MY:
                    dialogMoveCard = new CustomInputDialog(activity, R.style.Dialog_Fullscreen_title);
                    dialogMoveCard.setRdMsg("请移除密钥卡");
                    dialogMoveCard.setCanceledOnTouchOutside(false);
                    dialogMoveCard.setFinishIfDismiss(activity);
                    dialogMoveCard.show();
                    isInsertIcc(MES_ICC_DISMISS_DIALOG, false);
                    break;
                case MES_ICC_DISMISS_DIALOG:
                    if (null != dialogMoveCard && dialogMoveCard.isShowing()) {
                        dialogMoveCard.dismiss();
                    }
                    handler.sendEmptyMessage(MES_ICC_CARD_START_SWIPE_WH);
                    break;
                case MES_VIEW_START:
//                    if (v_wait.getVisibility() == View.GONE) {
//                        v_wait.setVisibility(View.VISIBLE);
//                        AnimationDrawable b = (AnimationDrawable) v_wait.getBackground();
//                        b.start();
//                    }
                    break;
                case MES_VIEW_STOP:
//                    AnimationDrawable b = (AnimationDrawable) v_wait.getBackground();
//                    b.stop();
//                    v_wait.setVisibility(View.GONE);
                    break;
                case MES_ICC_CARD_CHECK_PIN_MY:
                    toCheckPinMY((String) msg.obj);
                    break;
                case MES_ICC_CARD_CHECK_PIN_WH:
                    toCheckPinWH((String) msg.obj);
                    break;
                case MES_DOWNLOAD_TMK_BY_IC:
                    handler.sendEmptyMessage(MES_ICC_CARD_START_SWIPE_MY);
                    break;
                case MES_DOWNLOAD_TMK_BY_HAND:
                    handler.sendEmptyMessage(MES_ICC_CARD_START_SWIPE_WH);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_load_tmk_swipe_card;
    }

    @Override
    public String getAtyTitle() {
        return "";
    }

    @Override
    public void afterSetContentView() {
        rl_swipe = (RelativeLayout) findViewById(R.id.rl_swipe);
        rl_wait = (RelativeLayout) findViewById(R.id.rl_wait);
        v_wait = findViewById(R.id.v_wait);
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        include_title = findViewById(R.id.include_title);
        include_title.setVisibility(View.GONE);
        tv_swipe_tips = (TextView) findViewById(R.id.tv_swipe_tips);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        int type = MES_DOWNLOAD_TMK_BY_IC;
        if (null != intent) {
            type = intent.getIntExtra(TAG_DOWNLOAD_TMK_TYPE, MES_DOWNLOAD_TMK_BY_IC);
        }
        handler.sendEmptyMessage(type);
    }

    @Override
    public void save() {

    }

    private void isInsertIcc(final int msg, final boolean isCheckInsert) {
        isAlive = true;
        Log.i(TAG, "isInsertIcc()...");
        if (isCheckInsert) {
            rl_swipe.setVisibility(View.VISIBLE);
            rl_wait.setVisibility(View.GONE);
        }
        CardBinder mCardService = null;
        try {
            mCardService = ServiceManager.getInstence().getCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final CardBinder cardFinal = mCardService;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isAlive) {

                    if (cardFinal.isCardInster()) {
                        Log.i(TAG, "已插卡");
                        if (isCheckInsert) {
                            handler.sendEmptyMessage(msg);
                            break;
                        }
                    } else {
                        if (!isCheckInsert) {
                            handler.sendEmptyMessage(msg);
                            break;
                        }
                        Log.i(TAG, "未插卡");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }

    protected void setHint(String s) {
        Message msg = handler.obtainMessage(MES_VIEW_SET_HINT);
        msg.obj = s;
        msg.sendToTarget();
    }

    protected void start() {
        handler.sendEmptyMessage(MES_VIEW_START);
    }

    protected void stop() {
        handler.sendEmptyMessage(MES_VIEW_STOP);
    }

    private void toCheckPinMY(String pin) {
        Log.i(TAG, "toCheckPinMY()...");
        int ret;
        ret = IccPersonal.ResetCard();
        if (ret != ApduErrCode.SUCCESS) {
            Log.e(TAG, "ResetCard失败");
        } else {
            show("ResetCard成功");
        }
        show("开始校验密码");
        ret = IccPersonal.checkPin(pin);
        if (ret == 0) {
            show("密码验证成功");
            show("开始选择文件，并读出密钥条数");
            ret = IccPersonal.getTMKCount();
            show("选择文件，并读出密钥条数成功  ret=" + ret);
            if (ret > 0) {
                show("请输入密钥索引号");
                handler.sendEmptyMessage(MES_ICC_CARD_INPUT_INDEX);
            } else {
                showDialog(ApduErrCode.ProcErr(ret));
            }

        } else {
            Log.e(TAG, "密码验证失败");
            showDialog(ApduErrCode.ProcErr(ret));
        }
    }

    private void showDialog(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final CustomInputDialog dialogCustom = new CustomInputDialog(LoadTmkSwipeCardActivity.this, R.style.Dialog_Fullscreen_title);
                dialogCustom.setRdRightButton("确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialogCustom.dismiss();
                        finish();
                    }
                });
                dialogCustom.setRdMsg(s);
                dialogCustom.setCanceledOnTouchOutside(false);
                dialogCustom.setFinishIfDismiss(activity);
                dialogCustom.show();
            }
        });
    }

    private void show(String s) {
        TLog.d(s);
    }

    protected void toCheckPinWH(String pin) {
        Log.i(TAG, "toCheckPinMY()...");
        int ret;
        ret = IccPersonal.ResetCard();
        if (ret != ApduErrCode.SUCCESS) {
            Log.e(TAG, "ResetCard失败");
        } else {
            show("ResetCard成功");
        }
        show("开始校验密码");
        ret = IccPersonal.checkPin(pin);
        if (ret == 0) {
            show("密码验证成功");
            show("读有效期");
            ret = IccPersonal.loadTMK();
            String str = null;
            if (ret == 0) {
                str = "下载密钥成功";
            } else {
                str = ApduErrCode.ProcErr(ret);
            }
            show(str);
            showDialog(str);
        } else {
            show("密码验证失败");
            showDialog(ApduErrCode.ProcErr(ret));
        }
    }
}