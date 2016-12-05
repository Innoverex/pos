package cn.basewin.unionpay.utils;

import com.basewin.services.ServiceManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/9/5 14:30<br>
 * 描述:  <br>
 */
public class BeeperUtil {
    public static void defShort() {
        shortBeeper(3);
    }

    public static void defLong() {
        longBeeper();
    }

    /**
     * @param time  时间长短
     * @param hz    发声频率，单位HZ,1-10000均可
     * @param voice 发声大小 （无效）  默认传1
     */
    public static void beeper(int time, int hz, int voice) {
        try {
            ServiceManager.getInstence().getBeeper().beep(time, hz, voice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认短鸣
     *
     * @param count
     */
    public static void shortBeeper(final int count) {
        shortBeeper(count, 200);
    }

    public static boolean isStart = false;

    /**
     * @param count    鸣叫次数
     * @param interval 间隔时间
     */
    public static void shortBeeper(final int count, final long interval) {
        if (count <= 0) {
            return;
        }
        shortBeeper();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int i = count - 1;
                shortBeeper(i, interval);
            }
        }, interval + 200);
    }

    /**
     * 长鸣
     */
    public static void longBeeper() {
        TLog.l("长鸣");
        beeper(500, 1500, 1);
    }

    /**
     * 短鸣 间隔时间200ms
     */
    public static void shortBeeper() {
        TLog.l("短鸣");
        beeper(200, 750, 1);
    }

}
