package cn.basewin.unionpay.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/9/29 11:11<br>
 * 描述:  <br>
 */
public class BroadcastManage {
    public static String flag_nextFlow = "flag_nextFlow";

    public static IntentFilter getNextFlowFlag() {
        IntentFilter ift = new IntentFilter();
        ift.addAction(flag_nextFlow);
        return ift;
    }

    public static void sendNextFlowAction(Activity aty, int i) {
        Intent intent = new Intent();
        intent.putExtra("Broadcast_action", i);
        intent.setAction(flag_nextFlow);
        aty.sendBroadcast(intent);
    }

    public static int getNextFlowAction(Intent intent) {
        if (intent == null) {
            return 0;
        }
        int broadcast_action = intent.getIntExtra("Broadcast_action", 0);
        return broadcast_action;
    }
}
