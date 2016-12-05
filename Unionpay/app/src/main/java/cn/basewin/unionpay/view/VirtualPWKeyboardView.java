package cn.basewin.unionpay.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basewin.services.ServiceManager;
import com.basewin.utils.BCDHelper;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/29 10:56<br>
 * 描述: 密码键盘控件 <br>
 */
public class VirtualPWKeyboardView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = VirtualPWKeyboardView.class.getName();
    public TextView num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    public LinearLayout numok, numBack;
    private byte[] layout = new byte[96];

    public VirtualPWKeyboardView(Context context) {
        super(context);
        Log.e(TAG, "VirtualPWKeyboardView1");
        initView();
    }

    public VirtualPWKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "VirtualPWKeyboardView2");
        initView();
    }

    public VirtualPWKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View parent = View.inflate(this.getContext(), R.layout.aidl_keyboadview, this);
        numok = (LinearLayout) parent.findViewById(R.id.num_ok);
        num0 = (TextView) parent.findViewById(R.id.num0);
        num1 = (TextView) parent.findViewById(R.id.num1);
        num2 = (TextView) parent.findViewById(R.id.num2);
        num3 = (TextView) parent.findViewById(R.id.num3);
        num4 = (TextView) parent.findViewById(R.id.num4);
        num5 = (TextView) parent.findViewById(R.id.num5);
        num6 = (TextView) parent.findViewById(R.id.num6);
        num7 = (TextView) parent.findViewById(R.id.num7);
        num8 = (TextView) parent.findViewById(R.id.num8);
        num9 = (TextView) parent.findViewById(R.id.num9);
        numBack = (LinearLayout) parent.findViewById(R.id.num_back);
        setNUM("");
        setClick();
    }

    // 废弃：因为托管后点击是底层监听不给上层
    public void setButtonBG(int dro) {
        numok.setBackgroundResource(dro);
        num0.setBackgroundResource(dro);
        num1.setBackgroundResource(dro);
        num2.setBackgroundResource(dro);
        num3.setBackgroundResource(dro);
        num4.setBackgroundResource(dro);
        num5.setBackgroundResource(dro);
        num6.setBackgroundResource(dro);
        num7.setBackgroundResource(dro);
        num8.setBackgroundResource(dro);
        num9.setBackgroundResource(dro);
        numBack.setBackgroundResource(dro);
    }

    public void setClick() {
        numok.setOnClickListener(this);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        numBack.setOnClickListener(this);
    }

    public void setKeyShow(final byte[] keys) throws Exception {
        if (keys != null) {
            TLog.e(TAG, "键盘布局绘制中。。。。");
            TLog.e(TAG, TDevice.getMinTime());
            showViewXY();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    setPWLayout(keys);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);

    }

    private void setNUM(String msg) {
        num0.setText(msg);
        num1.setText(msg);
        num2.setText(msg);
        num3.setText(msg);
        num4.setText(msg);
        num5.setText(msg);
        num6.setText(msg);
        num7.setText(msg);
        num8.setText(msg);
        num9.setText(msg);
    }

    private void setPWLayout(byte[] keys) throws Exception {
        num1.setText(String.valueOf(keys[0] - 0x30));
        TLog.e(TAG, String.valueOf(keys[0] - 0x30));
        num2.setText(String.valueOf(keys[1] - 0x30));
        TLog.e(TAG, String.valueOf(keys[1] - 0x30));
        num3.setText(String.valueOf(keys[2] - 0x30));
        TLog.e(TAG, String.valueOf(keys[2] - 0x30));

        num4.setText(String.valueOf(keys[3] - 0x30));
        TLog.e(TAG, String.valueOf(keys[3] - 0x30));
        num5.setText(String.valueOf(keys[4] - 0x30));
        TLog.e(TAG, String.valueOf(keys[4] - 0x30));
        num6.setText(String.valueOf(keys[5] - 0x30));
        TLog.e(TAG, String.valueOf(keys[5] - 0x30));

        num7.setText(String.valueOf(keys[6] - 0x30));
        TLog.e(TAG, String.valueOf(keys[6] - 0x30));
        num8.setText(String.valueOf(keys[7] - 0x30));
        TLog.e(TAG, String.valueOf(keys[7] - 0x30));
        num9.setText(String.valueOf(keys[8] - 0x30));
        TLog.e(TAG, String.valueOf(keys[8] - 0x30));

        num0.setText(String.valueOf(keys[10] - 0x30));
        TLog.e(TAG, String.valueOf(keys[10] - 0x30));
        //回显按键顺便获取按键位置
        //According to random keyboard, and obtain the location of the keys
        int pos = 0;
        pos = addToByteArray(getWidgetPosition(num1), layout, pos);
        pos = addToByteArray(getWidgetPosition(num2), layout, pos);
        pos = addToByteArray(getWidgetPosition(num3), layout, pos);

        pos = addToByteArray(getWidgetPosition(num4), layout, pos);
        pos = addToByteArray(getWidgetPosition(num5), layout, pos);
        pos = addToByteArray(getWidgetPosition(num6), layout, pos);

        pos = addToByteArray(getWidgetPosition(num7), layout, pos);
        pos = addToByteArray(getWidgetPosition(num8), layout, pos);
        pos = addToByteArray(getWidgetPosition(num9), layout, pos);

        pos = addToByteArray(getWidgetPosition(numBack), layout, pos);
        pos = addToByteArray(getWidgetPosition(num0), layout, pos);
        pos = addToByteArray(getWidgetPosition(numok), layout, pos);
        TLog.e(TAG, "onWindowFocusChanged");
        TLog.e(TAG, TDevice.getMinTime());

        showViewXY();
        ServiceManager.getInstence().getPinpad().setPinpadLayout(layout);
//       ServiceManager.getInstence().getPinpad().setPinpadLayout(layout);
    }

    public static int i = 0;

    public void showViewXY() {
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num0), getWidgetPosition(num0).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num1), getWidgetPosition(num1).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num2), getWidgetPosition(num2).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num3), getWidgetPosition(num3).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num4), getWidgetPosition(num4).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num5), getWidgetPosition(num5).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num6), getWidgetPosition(num6).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num7), getWidgetPosition(num7).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num8), getWidgetPosition(num8).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(num9), getWidgetPosition(num9).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(numBack), getWidgetPosition(numBack).length));
        TLog.e(TAG, BCDHelper.hex2DebugHexString(getWidgetPosition(numok), getWidgetPosition(numok).length));


    }

    /**
     * 获取控件的位置
     * Get the widget coordinates
     *
     * @param widget
     * @return
     */
    public byte[] getWidgetPosition(View widget) {
        int[] location = new int[2];
        widget.getLocationOnScreen(location);
        int leftx, lefty, rightx, righty;
        leftx = location[0];
        lefty = location[1];
        rightx = location[0] + widget.getWidth();
        righty = location[1] + widget.getHeight();
        byte[] pos = new byte[8];
        // 0,768 0x0000 0x02fc
        // 0x00,0x00,0x02,0xfc

        byte[] tmp = BCDHelper.intToBytes2(leftx);
        byte[] tmp1 = BCDHelper.intToBytes2(lefty);
        byte[] tmp2 = BCDHelper.intToBytes2(rightx);
        byte[] tmp3 = BCDHelper.intToBytes2(righty);
        // 178,910 0x00b2 0x038e
        // 0x00,0xb2,0x03,0x8e
        // 左上x高位
        pos[0] = tmp[2];
        // 左上x低位
        pos[1] = tmp[3];
        // 左上y高位
        pos[2] = tmp1[2];
        // 左上y低位
        pos[3] = tmp1[3];
        // 右下x高位
        pos[4] = tmp2[2];
        // 右下x低位
        pos[5] = tmp2[3];
        // 右下y高位
        pos[6] = tmp3[2];
        // 右下y低位
        pos[7] = tmp3[3];
        return pos;
    }

    //将src的数组接到des的数组
    public int addToByteArray(byte[] src, byte[] dest, int position) {
        System.arraycopy(src, 0, dest, position, src.length);
        return position += src.length;
    }

    @Override
    public void onClick(View v) {
        TLog.e(TAG, "pw keyboard  click");
        switch (v.getId()) {
            case R.id.num0:
                TLog.l("0");
                break;
            case R.id.num1:
                TLog.l("1");
                break;
            case R.id.num2:
                TLog.l("2");
                break;
            case R.id.num3:
                TLog.l("3");
                break;
            case R.id.num4:
                TLog.l("4");
                break;
            case R.id.num5:
                TLog.l("5");
                break;
            case R.id.num6:
                TLog.l("6");
                break;
            case R.id.num7:
                TLog.l("7");
                break;
            case R.id.num8:
                TLog.l("8");
                break;
            case R.id.num9:
                TLog.l("9");
                break;

        }
    }
}
