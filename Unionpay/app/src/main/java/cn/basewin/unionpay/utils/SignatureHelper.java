package cn.basewin.unionpay.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

import cn.basewin.unionpay.AppConfig;


/**
 * Created by matengfei on 16/3/17.
 */
// 有锯齿效果
public class SignatureHelper {
    private static String TAG = SignatureHelper.class.getSimpleName();

    private ImageView iv_content;
    private Canvas canvas;
    private Paint paint;
    private Bitmap baseBitmap;
    private boolean bHasDraw = false;
    private int color_i = Color.TRANSPARENT;
    private Path path = new Path();
    private int width;
    private int height;
    private float startX;
    private float startY;
    private float clickX;
    private float clickY;

    private Activity activity;

    public void init(Activity activity, ImageView iv) {
        this.activity = activity;
        this.iv_content = iv;

        // 初始化一个画笔，笔触宽度为5
        paint = new Paint(); // 绘制样式物件
        paint.setAntiAlias(true); // 反锯齿
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE); // 设置画笔的风格，空心或者实心
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(8);


        iv_content.setOnTouchListener(touch);

    }

    public boolean isSign() {
        return bHasDraw;
    }

    public void onDestory() {
        if (baseBitmap != null) {
            baseBitmap.recycle();
            baseBitmap = null;
        }
    }


    private View.OnTouchListener touch = new View.OnTouchListener() {
        // 定义手指开始触摸的坐标
        float startX;
        float startY;
        float stopX;
        float stopY;

        PointF point_start = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                // 用户按下动作
                case MotionEvent.ACTION_DOWN:
                    // 第一次绘图初始化内存图片，指定背景为白色
                    if (baseBitmap == null) {
                        try {
                            baseBitmap = Bitmap.createBitmap(iv_content.getWidth(),
                                    iv_content.getHeight(), Bitmap.Config.ARGB_8888);
                            canvas = new Canvas(baseBitmap);
                            canvas.drawColor(color_i);
                            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
                        } catch (Exception e) {        //oom
                        }
                    }
                    // 记录开始触摸的点的坐标
                    startX = event.getX();
                    startY = event.getY();

                    point_start.set(startX, startY);
                    touchDown(startX, startY);
                    break;
                // 用户手指在屏幕上移动的动作
                case MotionEvent.ACTION_MOVE:
                    // 记录移动位置的点的坐标
                    float MoveX = event.getX();
                    float MoveY = event.getY();

                    //根据两点坐标，绘制连线
                    touchMove(MoveX, MoveY);

                    // 更新开始点的位置
                    startX = event.getX();
                    startY = event.getY();

                    // 把图片展示到ImageView中
                    iv_content.setImageBitmap(baseBitmap);
                    break;
                case MotionEvent.ACTION_UP:
                    stopX = event.getX();
                    stopY = event.getY();
                    float dx = Math.abs(stopX - point_start.x);
                    float dy = Math.abs(stopY - point_start.y);
                    Log.e(TAG, "dx: " + dx);
                    Log.e(TAG, "dy: " + dy);
                    if (dx > 10 || dy > 10) {
                        bHasDraw = true;
                    }

                    touchUp(event);


                    break;
                default:
                    break;
            }
            return true;
        }
    };

    void update() {
        this.canvas.drawPath(this.path, this.paint);
    }


    private void touchDown(float startX, float startY) {
        this.clickX = startX;
        this.clickY = startY;
        this.path.moveTo(startX, startY);
    }

    private void touchMove(float MoveX, float MoveY) {
        this.path.quadTo(this.clickX, this.clickY, (this.clickX + MoveX) / 2.0F, (this.clickY + MoveY) / 2.0F);
        this.clickX = MoveX;
        this.clickY = MoveY;
        update();
    }

    private void touchUp(MotionEvent event) {
        this.canvas.drawPath(this.path, this.paint);
        this.path.reset();
    }


    public Bitmap getBitmap() {
        return baseBitmap;
    }

    /**
     * 保存图片到SD卡上
     */
    public String saveBitmap() {
        try {
            // 保存图片到SD卡上
            File file = new File(AppConfig.DEFAULT_SAVE_IMAGE_PATH, "sign.png");
            //System.currentTimeMillis() + ".png");

            // 将背景色透明转换成白色保存
            Canvas canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.DARKEN);

            FileOutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Log.e(TAG, "保存图片成功");
            //Toast.makeText(activity, "保存图片成功", Toast.LENGTH_SHORT).show();

            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e(TAG, "保存图片失败");
            //Toast.makeText(activity, "保存图片失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return "";
    }


    public static String saveBitmap(Bitmap bitmap) {
        try {
            // 保存图片到SD卡上
            File dir = new File(AppConfig.DEFAULT_SAVE_IMAGE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(AppConfig.DEFAULT_SAVE_IMAGE_PATH, "sign_" + System.currentTimeMillis() + ".png");
            // 将背景色透明转换成白色保存
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.DARKEN);
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Log.e(TAG, "保存图片成功 " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e(TAG, "保存图片失败");
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 清除画板
     */
    public void resumeCanvas() {
        // 手动清除画板的绘图，重新创建一个画板
        if (baseBitmap != null) {
            baseBitmap = Bitmap.createBitmap(iv_content.getWidth(),
                    iv_content.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(color_i);
            iv_content.setImageBitmap(baseBitmap);
            bHasDraw = false;
        }
    }


}
