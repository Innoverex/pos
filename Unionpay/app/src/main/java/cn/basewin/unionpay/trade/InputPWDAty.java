package cn.basewin.unionpay.trade;

import android.content.Intent;
import android.os.RemoteException;

import cn.basewin.unionpay.base.BasePWDAty;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.utils.TLog;

/**
 * 内容摘要: <br>
 * 创建时间:  2016/7/19 09:48<br>
 * 描述: 实现确定的监听就可以了 <br>
 */
public class InputPWDAty extends BasePWDAty {
    public static final String KEY_PW = "InputPWAty_password";

    @Override
    public void onConfirm(byte[] bytes, boolean b) throws RemoteException {
        super.onConfirm(bytes, b);
        try {
            TLog.l("密码：" + new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FlowControl.MapHelper.setPWD(bytes);
        Card card = FlowControl.MapHelper.getCard();
        if (null != card) {
            card.password = bytes;
            FlowControl.MapHelper.setCard(card);
        }

        boolean ic = card.isICCard();
        if (!ic) {
            TLog.l("非ic卡");
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if(InputPWDAty.this!=null ||InputPWDAty.this.isFinishing()){
//                        startNextFlow();
//                    }
//                }
//            },2000);
            startNextFlow();
        }

    }

    @Override
    public void onAARequestOnlineProcess(Intent intent) throws RemoteException {
        super.onAARequestOnlineProcess(intent);
        TLog.l("onAARequestOnlineProcess 回调后执行流程");
        startNextFlow();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(InputPWDAty.this!=null ||InputPWDAty.this.isFinishing()){
//                    startNextFlow();
//                }
//            }
//        },2000);
    }
}
