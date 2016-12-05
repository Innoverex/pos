package cn.basewin.unionpay.utils;

import com.basewin.services.ServiceManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/9/5 15:31<br>
 * 描述: 程序异常终止后 灯还亮 着这个问题无法解决 <br>
 */
public class LedUtil {
    private static final int LED_BLUE = 0x10;
    private static final int LED_GREEN = 0x20;
    private static final int LED_YELLOW = 0x40;
    private static final int LED_RED = 0x80;

    private final static long jiange = 200;


    public static void open(int led, boolean off) {
        try {
            ServiceManager.getInstence().getLed().enableLedIndex(led, off);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>激活卡片>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //寻卡开启

    /**
     * 激活卡片
     */
    public static void startActivation() {
        TLog.l("激活卡片灯");
        blueAlways();
    }

    /**
     * 停止激活卡片
     */
    public static void stopActivation() {
        blueClose();
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>空闲状态>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //蓝灯每间隔5秒闪烁一次，每次亮灯时长大约 200 毫秒，其它灯不亮
    //刷卡界面开启时开启

    /**
     * 开启空闲状态下灯
     */
    public static void startFree() {
        TLog.l("开启空闲状态下灯");
        blueLoop(5000);
        greenClose();
        yellowClose();
        redClose();
    }

    public static void stopFree() {
        TLog.l("停止空闲状态下灯");
        blueClose();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>交易处理>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //（1）读卡器正在读取支付卡片数据或（2）读卡器完成支付卡片数据读取并正在读取非支付应用数据
    //（1） 读卡器只处理支付应用时， 蓝灯仍然保持常亮，黄色灯亮起（2）读卡器完成卡片支付数据读取并正在处理非支付应用数据时黄色指示灯亮
    //在流程开启的时候开启
    public static void startTransaction() {
        blueAlways();
        yellowAlways();
    }

    public static void stopTransaction() {
        blueClose();
        yellowClose();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>移出卡片>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //所有数据均已读取（对于脱机交易，卡片移出感应区后需进行 DDA 验证）
    //蓝灯、 黄灯仍然保持常亮。
    //持续长蜂鸣音
    public static void starTremove() {
        blueAlways();
        yellowAlways();
        BeeperUtil.defLong();
    }

    public static void stopTremove() {
        blueClose();
        yellowClose();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>联机交易>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //对于联机交易，绿色指示灯闪烁，直至发卡方认证结束
    public static void starNet() {
        greenLoop(1000);
    }


    public static void stopNet() {
        greenClose();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>交易成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //脱机DDA校验已成功，或者联机认证已成功。
    //维持移出卡片时的状态，并显示交易成功。绿灯亮起，三色灯同时亮至少 750 毫秒后，全部熄灭。（对于联机交易，绿色指示灯闪烁，直至发卡方认证结束）。
    public static void starSuccess() {
        blueClose(750);
        yellowClose(750);
        blueClose(750);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>交易失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //交易过程发生错误
    //红灯常亮，并提示相应的错误信息，包括：多卡冲突、转接触式或磁条方式交易、卡片未移出等。
    public static void starFail() {
        redAlways();
        BeeperUtil.defShort();
    }

    public static void stopFail() {
        closeAll();
    }

    /**
     * 关闭全部的灯
     */
    public static void closeAll() {
        blueClose();
        greenClose();
        yellowClose();
        redClose();
    }

    public static void end() {
        closeAll();
    }

    //--------------------------------------------蓝色
    private static int blueType = 0;

    public static void blueLoop(final long l) {
        if (blueType == 1) {
            return;
        }
        blueType = 1;
        new Thread() {
            @Override
            public void run() {
                super.run();
                startBlueLoop(l);
            }
        }.start();

    }

    public static void startBlueLoop(long l) {
        try {
            open(LED_BLUE, true);
            TLog.l(TDevice.getSysTime());
            TLog.l("间隔时间：" + l);
            new Thread().sleep(jiange);
            TLog.l(TDevice.getSysTime());
            open(LED_BLUE, false);
            new Thread().sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (blueType == 1) {
            startBlueLoop(l);
        }
    }

    public static void blueAlways() {
        if (blueType == 2) {
            return;
        }
        blueType = 2;
        open(LED_BLUE, true);
    }

    public static void blueClose(long t) {
        blueType = 3;
        open(LED_BLUE, true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                open(LED_BLUE, false);
            }
        }, t);

    }

    public static void blueClose() {
        blueType = 3;
        open(LED_BLUE, false);
    }

    //--------------------------------------------绿色色
    private static int greenType = 0;

    public static void greenLoop(final long l) {
        if (greenType == 1) {
            return;
        }
        greenType = 1;
        new Thread() {
            @Override
            public void run() {
                super.run();
                startGreenLoop(l);
            }
        }.start();

    }

    public static void startGreenLoop(long l) {
        try {
            open(LED_GREEN, true);
            TLog.l(TDevice.getSysTime());
            TLog.l("间隔时间：" + l);
            new Thread().sleep(jiange);
            TLog.l(TDevice.getSysTime());
            open(LED_GREEN, false);
            new Thread().sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (greenType == 1) {
            startGreenLoop(l);
        }
    }

    public static void greenAlways() {
        if (greenType == 2) {
            return;
        }
        greenType = 2;
        open(LED_GREEN, true);
    }

    public static void greenClose(long t) {
        greenType = 3;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                open(LED_GREEN, false);
            }
        }, t);

    }

    public static void greenClose() {
        greenType = 3;
        open(LED_GREEN, false);
    }

    //--------------------------------------------黄色
    private static int yellowType = 0;

    public static void yellowLoop(final long l) {
        if (yellowType == 1) {
            return;
        }
        yellowType = 1;
        new Thread() {
            @Override
            public void run() {
                super.run();
                startYellowLoop(l);
            }
        }.start();

    }

    public static void startYellowLoop(long l) {
        try {
            open(LED_YELLOW, true);
            TLog.l(TDevice.getSysTime());
            TLog.l("间隔时间：" + l);
            new Thread().sleep(jiange);
            TLog.l(TDevice.getSysTime());
            open(LED_YELLOW, false);
            new Thread().sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (yellowType == 1) {
            startYellowLoop(l);
        }
    }

    public static void yellowAlways() {
        if (yellowType == 2) {
            return;
        }
        yellowType = 2;
        open(LED_YELLOW, true);
    }

    public static void yellowClose(long t) {
        yellowType = 3;
        open(LED_YELLOW, true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                open(LED_YELLOW, false);
            }
        }, t);

    }

    public static void yellowClose() {
        yellowType = 3;
        open(LED_YELLOW, false);
    }

    //--------------------------------------------红色
    private static int redType = 0;

    public static void redLoop(final long l) {
        if (redType == 1) {
            return;
        }
        redType = 1;
        new Thread() {
            @Override
            public void run() {
                super.run();
                startRedLoop(l);
            }
        }.start();

    }

    public static void startRedLoop(long l) {
        try {
            open(LED_RED, true);
            TLog.l(TDevice.getSysTime());
            TLog.l("间隔时间：" + l);
            new Thread().sleep(jiange);
            TLog.l(TDevice.getSysTime());
            open(LED_RED, false);
            new Thread().sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (redType == 1) {
            startRedLoop(l);
        }
    }

    public static void redAlways() {
        if (redType == 2) {
            return;
        }
        redType = 2;
        open(LED_RED, true);
    }

    public static void redClose(long t) {
        redType = 3;
        open(LED_RED, true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                open(LED_RED, false);
            }
        }, t);

    }

    public static void redClose() {
        redType = 3;
        open(LED_RED, false);
    }

}
