package cn.basewin.unionpay.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.basewin.unionpay.R;


public class KeyBoardView implements OnClickListener {

    public KeyBoardView(Context context) {
        this.mContext = context;
    }

    private Context mContext;
    public TextView num00, num0, num1, num2, num3, num4, num5, num6, num7, num8,
            num9;
    public RelativeLayout numCancel;
    public RelativeLayout numBack;
    public RelativeLayout numOk;

    public View getKeyBoardView() {
        View parent = LinearLayout.inflate(mContext, R.layout.shuzijianpan, null);
        num00 = (TextView) parent.findViewById(R.id.num00);
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
        numCancel = (RelativeLayout) parent.findViewById(R.id.num_cancel);
        numBack = (RelativeLayout) parent.findViewById(R.id.num_back);
        numOk = (RelativeLayout) parent.findViewById(R.id.num_ok);
        num00.setOnClickListener(this);
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
        numCancel.setOnClickListener(this);
        numBack.setOnClickListener(this);
        numOk.setOnClickListener(this);

        return parent;
    }


    @Override
    public void onClick(View v) {
        if (numKeyListener != null) {
            numKeyListener.onClick(v);
        }
    }

    /**
     * 设置键盘监听
     *
     * @param listener
     */
    public void setNumKeyListener(NumKeyListener listener) {
        this.numKeyListener = listener;
    }

    private NumKeyListener numKeyListener;

    public interface NumKeyListener {
        public void onClick(View view);
    }


}
