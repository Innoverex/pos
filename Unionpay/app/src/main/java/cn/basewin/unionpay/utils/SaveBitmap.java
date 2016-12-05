package cn.basewin.unionpay.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import com.basewin.utils.BCDHelper;
import com.odm.tools.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class SaveBitmap {

    public static final String SDPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    private static String TAG = "SaveBitmap";

    CommParam comParam = new CommParam();

    protected static void writeWord(FileOutputStream stream, int value)
            throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        if (TLog.isDebug) {
            Log.d(TAG, "value = " + value);
            Log.d(TAG, "writeWord =" + BCDHelper.hex2DebugHexString(b, 2));
        }
        stream.write(b);
    }

    protected static void writeDword(FileOutputStream stream, long value)
            throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        if (TLog.isDebug) {
            Log.d(TAG, "value = " + value);
            Log.d(TAG, "writeDword =" + BCDHelper.hex2DebugHexString(b, 4));
        }
        stream.write(b);
    }

    protected static void writeLong(FileOutputStream stream, long value)
            throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    // BMP= 位图文件头，位图信息头，位图阵列
    // BMP文件头
    private byte[] addBMPImageHeader(int size) {
        byte[] buffer = new byte[14];
        // 文件表识 2 bytes
        buffer[0] = 0x42;
        buffer[1] = 0x4D;

        // File Size 1 dword
        buffer[2] = (byte) (size >> 0);
        buffer[3] = (byte) (size >> 8);
        buffer[4] = (byte) (size >> 16);
        buffer[5] = (byte) (size >> 24);

        // Reserved 1dword
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;

        // bitmap Data Offset 1dword
        // 从文件开始到位图数据开始之间的数据(bitmap data) 之间的偏移量
        // buffer[10] = 0x36; //54
        buffer[10] = 0x3E; // 54+8=62位 8字节调色板
        buffer[11] = 0x00;
        buffer[12] = 0x00;
        buffer[13] = 0x00;
        return buffer;
    }

    // BMP文件信息头
    private byte[] addBMPImageInfosHeader(int w, int h) {
        byte[] buffer = new byte[40];
        buffer[0] = 0x28; // Bitmap Info Header length
        buffer[1] = 0x00;
        buffer[2] = 0x00;
        buffer[3] = 0x00;

        buffer[4] = (byte) (w >> 0); // bitmap width
        buffer[5] = (byte) (w >> 8);
        buffer[6] = (byte) (w >> 16);
        buffer[7] = (byte) (w >> 24);

        buffer[8] = (byte) (h >> 0); // bitmap Height
        buffer[9] = (byte) (h >> 8);
        buffer[10] = (byte) (h >> 16);
        buffer[11] = (byte) (h >> 24);

        buffer[12] = 0x01; // 位面数 planes 1word 该值总为1

        buffer[13] = 0x00;

        // 1word bits per pixel
        // 1 - 单色位图
        // 4 - 16 色位图
        // 8 - 256 色位图
        // 16 - 16bit 高彩色位图
        // 24 - 24bit 真彩色位图
        // 32 - 32bit 增强型真彩色位图
        // TODO：
        // buffer[14] = 0x18; //真彩色位图
        buffer[14] = 0x01; // 单色位图

        buffer[15] = 0x00;

        // compression 1dword
        buffer[16] = 0x00;
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;

        // bitmap data size 1dword 该数必须所4的倍数

        // buffer[20] = 0x00;
        // buffer[21] = 0x00;
        // buffer[22] = 0x00;
        // buffer[23] = 0x00;

        int datasize = h * h >> 3;
        if ((w * h >> 3) % 4 != 0) {

            datasize++;
        }
        System.out.println("-------datasize= " + datasize);

        buffer[20] = (byte) (datasize >> 0); // bitmap width
        buffer[21] = (byte) (datasize >> 8);
        buffer[22] = (byte) (datasize >> 16);
        buffer[23] = (byte) (datasize >> 24);

        // HResolutison 1dword 用象素/米表示的水平分辨率
        buffer[24] = (byte) 0x00;
        buffer[25] = 0x04;
        buffer[26] = 0x00;
        buffer[27] = 0x00;

        // VResolution 1dword 用象素/米表示的垂直分辨率
        buffer[28] = 0x02;
        buffer[29] = 0x03;
        buffer[30] = 0x00;
        buffer[31] = 0x00;

        // colors 1dword 位图使用的颜色数
        // 如果为0的话，则说明使用所用调色板项
        buffer[32] = 0x02; // 颜色数为2
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;

        // Important Colors 1 dword 定重要的颜色数
        buffer[36] = 0x00;
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;

        return buffer;
    }

    private byte[] addBMP_RGB_888(int[] b, int w, int h) {
        int len = b.length;
        System.out.println(b.length);

        // byte[] buffer = new byte[w*h * 3];//每个象素三个字节
        // 单色二值图片 2个色 1bit位表示颜色 0白色 1黑色
        byte[] buffer = new byte[w * h * 1 >> 3];

        int offset = 0;
        // 一行一行的读数据，从左到右，从下到上
        for (int i = len - 1; i >= w; i -= w) {
            // DIB文件格式最后一行为第一行，每行按从左到右顺序
            System.out.println("i= " + i);
            int end = i, start = i - w + 1;
            // 四个字节字节的读写
            for (int j = start; j <= end; j++) {

                // TODO：
                buffer[offset] = (byte) (b[j] >> 0); // ？？？
                buffer[offset + 1] = (byte) (b[j] >> 8);
                buffer[offset + 1] = (byte) (b[j] >> 16);
                offset += 3;

            }
        }
        return buffer;
    }

    // 调色板 0白色 1 黑色 2个数值索引号
    // 索引号就是所在行的行号，对应的颜色就是所在行的四个元素。
    private byte[] addBMP_Palette() {
        byte[] buffer = new byte[2 * 4];

        // FF FF FF 00 //白色
        // FF FF 00 00 黑色
        // 白色 第一个索引号对应的颜色
        buffer[0] = (byte) 0xff;
        buffer[1] = (byte) 0xff;
        buffer[2] = (byte) 0xff;
        buffer[3] = 0x00;

        // 黑色 第二个索引号对应的颜色

        // 0xff 0x00 0xff 0x00 紫色

        // 0xff 0x00 0x00 0x00 蓝色
        // 0x00 0xff 0x00 0x00 录色
        // 0x00 0x00 0xff 0x00 红色

        // 0xff 0xff 0x00 0x00 白色
        // 0x00 0x00 0x00 0x00 //黑色

        buffer[4] = 0x00;
        buffer[5] = 0x00;
        buffer[6] = 0x00;
        buffer[7] = 0x00;

        return buffer;
    }

    // 位图数据

    private byte[] addBMP_mapData(int w, int h) {
        int m_iBitsPerPixel = 2;// 每相素字节数
        int m_iImageWidth = w; // 图片宽
        int m_iImageHeight = h;
        // 填充后的每行的字节数为：
        int iLineByteCnt = (((m_iImageWidth * m_iBitsPerPixel) >> 5)) << 2;
        // 位图数据区的大小为：
        int m_iImageDataSize = iLineByteCnt * m_iImageHeight;

        return null;
    }

    // TODO:peng,20140716
    // /// 测试 读取现成的图片bmp,然后再重新往写一个新的文件

    // 保存到 传入的路径中
    public void saveBmp_PNG(Bitmap bm, String SavePath, String filename) {
        if (SavePath == null || filename == null)
            return;
        File file = new File(SavePath);
        if (!file.exists())
            file.mkdirs();
        filename = SavePath + filename;
        file = new File(filename);

        try {
            if (!file.createNewFile()) {
                Log.d(TAG, "file.createNewFile() fail...");
            }
        } catch (IOException ex) {
        }

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存到 缩小的图片
    public void saveBmp_PNG_small(Bitmap bm, String SavePath, String filename) {

        // 缩小图片
        bm = smallBitmap(bm);

        if (SavePath == null || filename == null)
            return;

        File file = new File(SavePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        // 当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(currentTime);

        if (TLog.isDebug)
            Log.d(TAG, " 保存缩小后图片  filename : " + filename);

        filename = SavePath + filename;

        System.out.println("filename = " + filename);
        file = new File(filename);

        try {
            if (!file.createNewFile()) {
                Log.d(TAG, "file.createNewFile() fail...");
            }
        } catch (IOException ex) {
            Log.d(TAG, " &&&&&   IOException");
        }

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

	/* 保存二值点阵图像 */
    // 保存文件为pbm 文件格式

    // 三种文件格式结构相同，都非常简单，没有压缩。由表头和图像数据两部分组成。表头数据各项之间用空格(空格键、制表键、回车键或换行键)隔开,表头由四部分组成:
    // ① 文件描述子:指明文件的类型以及图像数据的存储方式;
    // ② 图像宽度;
    // ③ 图像高度;
    // ④ 最大灰度值或颜色值.
    public boolean saveBitmapTopbm(Bitmap bitmap) {
        if (bitmap == null)
            return false;
        // 位图大小
        int nBmpWidth = bitmap.getWidth();
        int nBmpHeight = bitmap.getHeight();
        Log.i("kxf", "图片宽高：" + nBmpWidth + ":" + nBmpHeight);
        // 图像数据大小
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);

        // add new
        int datasize = nBmpHeight * (nBmpWidth / 8 + nBmpWidth % 8);

        try {
            // 存储文件名
            String SavePath = getSDCardPath() + "/sign/pbmFile";
            File file = new File(SavePath);

            if (!file.exists()) {
                file.mkdirs();
            }

            long currentTime = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(currentTime);
            String filename = SavePath + "/" + formatter.format(date) + ".pbm";
            filename = SavePath + "/1.pbm";
            comParam.setJbgFileSavePath(SavePath);
            comParam.setJbgFileName("2.jbig");
            file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileos = new FileOutputStream(filename);
            // pbm 文件头
            // “ p4 ”
            byte[] filetype = new byte[2];
            filetype[0] = (byte) 0x50;
            filetype[1] = (byte) 0x34;

            fileos.write(filetype);

            // 宽长度
            // byte[] widthDataLen = new byte[1];
            // widthDataLen[0] = 0x0A;
            // fileos.write(widthDataLen);

            // 图宽
            // byte[] widthData = new byte[10];
            // for (int i = 0; i < 10; i++)
            // widthData[i] = (byte) 0x20;
            // // 400
            // widthData[7] = 0x34;
            // widthData[8] = 0x30;
            // widthData[9] = 0x30;
            // fileos.write(widthData);
            //
            // // 高长度
            // // byte[] heghitDataLen = new byte[1];
            // // heghitDataLen[0] = 0x0A;
            // // fileos.write(heghitDataLen);
            //
            // // 图高
            // byte[] heghitData = new byte[10];
            // for (int i = 0; i < 10; i++)
            // heghitData[i] = (byte) 0x20;
            // // 160
            // heghitData[7] = 0x31;
            // heghitData[8] = 0x36;
            // heghitData[9] = 0x30;
            // fileos.write(heghitData);

            // //方法二：400*160

            byte[] widthDataLen = new byte[1];
            widthDataLen[0] = 0x0A;
            fileos.write(widthDataLen);

            byte[] widthData = new byte[4];
            // 400
            widthData[0] = 0x39;
            widthData[1] = 0x36;
            //widthData[2] = 0x30;
            widthData[3] = 0x20;// 空格符
            fileos.write(widthData);

            // 图高
            byte[] heghitData = new byte[3];
            // 160
            heghitData[0] = 0x31;
            heghitData[1] = 0x37;
            heghitData[2] = 0x33;
            fileos.write(heghitData);

            // 最大象素 无

            // 间隔符
            byte[] maxpixelLen = new byte[1];
            maxpixelLen[0] = 0x0A;
            fileos.write(maxpixelLen);

            // old
            //byte[] bitmapdata = bitmap2PrinterBytes_v_r(bitmap);
            byte[] bitmapdata = bitmap2PrinterBytes_h_r(bitmap);
            fileos.write(bitmapdata);
            fileos.flush();
            fileos.close();

            // 如果签字黑色象素点数不大于总共的整体象素的95%，保存图片，否则不做任何操作
            // 确定后提示电子签字压缩失败
            //add by kxf 不判断 2016年9月5日16:03:34
//            if (comParam.getBlackPixel() > 6) {
//                Log.e(TAG, "签字黑色象素点数不大于总共的整体象素的95%，保存图片，否则不做任何操作");
//                return false;
//            }

            // 把pbm图片进行jbig压缩
            return CMDCompressPbmToJbg(filename);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // public void CreatFile(String FilePath, String FileName) {
    // if (SystemInfo.ENABLE_DEBUG)
    // Log.d(TAG, "start CreatFile");
    // try {
    // File file = new File(FilePath);
    //
    // if (!file.exists()) {
    // file.mkdirs();
    // }
    //
    // FileName = FilePath + FileName;
    // file = new File(FileName);
    //
    // if (!file.exists()) {
    // file.createNewFile();
    // }
    //
    // } catch (FileNotFoundException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    //
    // if (SystemInfo.ENABLE_DEBUG)
    // Log.d(TAG, "end saveBitmapToBmp");
    //
    // }

    public void CreatFile(String FilePath, String FileName) {
        if (TLog.isDebug)
            Log.d(TAG, "start CreatFile");
        File file = new File(FilePath);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    // 通过命令压缩PbmToJbg文件
    boolean CMDCompressPbmToJbg(String filename) {
        Log.i(TAG, "CMDCompressPbmToJbg()...通过命令压缩PbmToJbg文件");
        String jbgPath = comParam.getJbgFileSavePath()
                + "/" + comParam.getJbgFileName();
        if (TLog.isDebug)
            Log.d(TAG, "save jbgPath : " + jbgPath);
        // Tools.system("pbmtools " + filename + " " + jbgPath);

        CreatFile(comParam.getJbgFileSavePath(), comParam.getJbgFileName());
        int system = Tools.system("compressTojbg " + filename + " " + jbgPath);
        Log.i("kxf", "xianying:system=" + system);
        if (0 == system) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能： 二值点阵图像 例子
     **/

    public static Bitmap smallBitmap(Bitmap bmp) {
        if (TLog.isDebug)
            Log.d(TAG, "start to the smallBitmap()...");

        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        float scaleWidth = 1;
        float scaleHeight = 1;

		/* 设置图片缩小的比例 */
        // double scale = 0.2; //缩小后 图片解析不出来
        // double scale = 0.5; //缩小后 图片太小
        double scale = 0.4;
        scale = 0.6; // 压缩后需亚8的倍数

		/* 计算这次要缩小的比例 */
        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
                matrix, true);

        return resizeBmp;
    }

    // add-pjp-20150205
    public static byte[] bitmap2PrinterBytes_v(Bitmap bitmap) {
        if (TLog.isDebug)
            Log.d(TAG, "start to the bitmap2PrinterBytes_v()....");

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        byte[] tmpBuf = new byte[(height / 8 + 1) * width + 1];
        byte[] realBuf;
        int[] p = new int[8];
        int t = 0;

        // horizontal move
        for (int x = 0; x < width; x++) {
            // vertical move
            for (int y = 0; y < height; y = y + 8) {
                // get one byte in 8 times
                for (int m = 0; m < 8; m++) {
                    if (y + m >= height) {
                        p[m] = 0;
                    } else {
                        p[m] = bitmap.getPixel(x, y + m) == -1 ? 0 : 1;
                    }
                }
                // calculate byte value
                int value = p[0] * 128 + p[1] * 64 + p[2] * 32 + p[3] * 16
                        + p[4] * 8 + p[5] * 4 + p[6] * 2 + p[7];
                tmpBuf[++t] = (byte) value;
            }
        }
        realBuf = new byte[t + 1];
        for (int i = 0; i < t + 1; i++) {
            realBuf[i] = tmpBuf[i];
        }
        return realBuf;
    }

    // public static byte[] bitmap2PrinterBytes_v_r(Bitmap bitmap) {
    // if (SystemInfo.ENABLE_DEBUG)
    // Log.d(TAG, "start to the bitmap2PrinterBytes_v()....");
    //
    // int height = bitmap.getHeight();
    // int width = bitmap.getWidth();
    //
    // // byte[] tmpBuf = new byte[(height / 8 + 1) * width + 1];
    // // TODO:pjp-20150227
    //
    // byte[] tmpBuf = new byte[(width / 8 + 1) * height + 1];
    //
    // byte[] realBuf;
    // int[] p = new int[8];
    // int t = 0;
    //
    // // 有偏移 图片整体会向右偏移
    // // horizontal move
    // // for (int y = 0; y < height; y++) {
    // // // vertical move
    // // for (int x = 0; x < width; x = x + 8) {
    // // // get one byte in 8 times
    // // for (int m = 0; m < 8; m++) {
    // // if (x + m >= width) {
    // // p[m] = 0;
    // // } else {
    // // p[m] = bitmap.getPixel(x + m, y) == -1 ? 0 : 1;
    // //
    // // }
    // // }
    // // // calculate byte value
    // // int value = p[0] * 128 + p[1] * 64 + p[2] * 32 + p[3] * 16
    // // + p[4] * 8 + p[5] * 4 + p[6] * 2 + p[7];
    // //
    // //
    // // // old
    // // // tmpBuf[++t] = (byte) value;
    // // tmpBuf[t++] = (byte) value;
    // //
    // // }
    // // }
    //
    // for (int y = 0; y < height; y++) {
    // // vertical move
    // for (int x = 0; x < width; x = x + 8) {
    // // get one byte in 8 times
    // for (int m = 0; m < 8; m++) {
    // if (x + m >= width) {
    // p[m] = 0;
    // } else {
    // //0 白色 1 黑色
    // p[7 - m] = bitmap.getPixel(x + m, y) == -1 ? 0 : 1;
    // if(p[7-m] == 1){ //此象素点为黑色
    //
    // }
    //
    // }
    // }
    // // calculate byte value 这样图片没有偏移
    // int value = p[7] * 128 + p[6] * 64 + p[5] * 32 + p[4] * 16
    // + p[3] * 8 + p[2] * 4 + p[1] * 2 + p[0];
    //
    // // old
    // // tmpBuf[++t] = (byte) value;
    // tmpBuf[t++] = (byte) value;
    // }
    // }
    //
    // // realBuf = new byte[t + 1];
    // // for (int i = 0; i < t + 1; i++) {
    // // realBuf[i] = tmpBuf[i];
    // // }
    //
    // realBuf = new byte[t];
    // for (int i = 0; i < t; i++) {
    // realBuf[i] = tmpBuf[i];
    // }
    //
    // return realBuf;
    // }

    public byte[] bitmap2PrinterBytes_v_r(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        // byte[] tmpBuf = new byte[(height / 8 + 1) * width + 1];
        // TODO:pjp-20150227

        byte[] tmpBuf = new byte[(width / 8 + 1) * height + 1];

        byte[] realBuf;
        int[] p = new int[8];
        int t = 0;

        int blackpixelNum = 0;

        for (int y = 0; y < height; y++) {
            // vertical move
            for (int x = 0; x < width; x = x + 8) {
                // get one byte in 8 times
                for (int m = 0; m < 8; m++) {
                    if (x + m >= width) {
                        p[m] = 0;
                    } else {
                        // 0 白色 1 黑色
                        p[7 - m] = bitmap.getPixel(x + m, y) == -1 ? 0 : 1;
                        if (p[7 - m] == 1) { // 此象素点为黑色
                            blackpixelNum++;
                        }

                    }
                }
                // calculate byte value 这样图片没有偏移
                int value = p[7] * 128 + p[6] * 64 + p[5] * 32 + p[4] * 16
                        + p[3] * 8 + p[2] * 4 + p[1] * 2 + p[0];

                // old
                // tmpBuf[++t] = (byte) value;
                tmpBuf[t++] = (byte) value;
            }
        }

        realBuf = new byte[t];
        for (int i = 0; i < t; i++) {
            realBuf[i] = tmpBuf[i];
        }

        // add-by-pjp-20150228
        // 保存签字的黑色的象素点总数
        if (TLog.isDebug)
            Log.d(TAG,
                    "blackpixelNum : " + blackpixelNum + ", totalPixelNum : "
                            + ((bitmap.getWidth() * bitmap.getHeight())));


        CalSignBlackPixelPer(blackpixelNum, bitmap.getWidth(), bitmap.getHeight());


        // 计算黑色像素占整个象素百分
//		int blackPixPer = 0;
//		blackPixPer = blackpixelNum * 100
//				/ (bitmap.getWidth() * bitmap.getHeight());
//		comParam.setBlackPixel(blackPixPer);
//		if (SystemInfo.ENABLE_DEBUG)
//			Log.d(TAG, "blackPixPer : " + blackPixPer + "%");

        return realBuf;
    }

    public byte[] bitmap2PrinterBytes_h(Bitmap bitmap) {
        if (TLog.isDebug)
            Log.d(TAG, "start to the bitmap2PrinterBytes_h()....");

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        if (TLog.isDebug) {
            Log.d(TAG, "height : " + height);
            Log.d(TAG, "width : " + width);
        }

        byte[] tmpBuf = new byte[(height / 8 + 1) * width + 1];
        byte[] realBuf;
        int[] p = new int[8];
        int t = 0;

        // horizontal move
        for (int y = height - 1; y >= 0; y--) {
            // vertical move
            for (int x = 0; x < width; x = x + 8) {
                // get one byte in 8 times
                for (int m = 0; m < 8; m++) {
                    if (x + m >= width) {
                        p[m] = 0;
                    } else {
                        p[m] = bitmap.getPixel(x + m, y) == -1 ? 0 : 1;

                    }
                }
                // calculate byte value
                int value = p[0] * 128 + p[1] * 64 + p[2] * 32 + p[3] * 16
                        + p[4] * 8 + p[5] * 4 + p[6] * 2 + p[7];
                // old
                tmpBuf[++t] = (byte) value;

                // new
                // tmpBuf[t++] = (byte)value;

            }
        }
        realBuf = new byte[t + 1];
        for (int i = 0; i < t + 1; i++) {
            realBuf[i] = tmpBuf[i];
        }


        return realBuf;
    }

    // bmp 图片是反的
    public byte[] bitmap2PrinterBytes_h_r(Bitmap bitmap) {
        if (TLog.isDebug)
            Log.d(TAG, "start to the bitmap2PrinterBytes_h_r()....");

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (TLog.isDebug) {
            Log.d(TAG, "width : %d " + width);
            Log.d(TAG, "height : %d" + height);
        }

        byte[] tmpBuf = new byte[(height / 8 + 1) * width + 1];
        byte[] realBuf;
        int[] p = new int[8];
        int t = 0;
        int blackpixelNum = 0;
        // horizontal move
        for (int y = 0; y < height; y++) {
            // vertical move
            for (int x = 0; x < width; x = x + 8) {
                // get one byte in 8 times
                for (int m = 0; m < 8; m++) {
                    if (x + m >= width) {
                        p[m] = 0;
                    } else {
                        p[m] = bitmap.getPixel(x + m, y) == -1 ? 0 : 1;
                        if (p[m] == 1) { // 此象素点为黑色
                            blackpixelNum++;
                        }
                    }
                }
                // calculate byte value
                int value = p[0] * 128 + p[1] * 64 + p[2] * 32 + p[3] * 16
                        + p[4] * 8 + p[5] * 4 + p[6] * 2 + p[7];
                tmpBuf[++t] = (byte) value;
            }
        }
        realBuf = new byte[t + 1];
        for (int i = 0; i < t + 1; i++) {
            realBuf[i] = tmpBuf[i];
        }

        if (TLog.isDebug)
            Log.d(TAG,
                    "blackpixelNum : " + blackpixelNum + ", totalPixelNum : "
                            + ((bitmap.getWidth() * bitmap.getHeight())));


        CalSignBlackPixelPer(blackpixelNum, bitmap.getWidth(), bitmap.getHeight());
        return realBuf;
    }

    public void CalSignBlackPixelPer(int blackpixelNum, int bitmapWidth, int bitmapHight) {
        // 计算黑色像素占整个象素百分
        int blackPixPer = 0;
        blackPixPer = blackpixelNum * 100
                / (bitmapWidth * bitmapHight);
        comParam.setBlackPixel(blackPixPer);
        if (TLog.isDebug)
            Log.d("liangfeng", "blackPixPer : " + blackPixPer + "%" + " , refBlackPixelPer :" + comParam.getRefBlackPixelPer() + " %");
    }

}
