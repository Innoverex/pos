package cn.basewin.unionpay.trade;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.SaveBitmap;

import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/8/29.
 * 上送电子签名等待
 */
public class NetUploadSignaWaitAty extends BaseWaitAty {
    private cn.basewin.unionpay.utils.SaveBitmap bitmap2 = new cn.basewin.unionpay.utils.SaveBitmap();
    private String path;
    public static final String KEY_DATA_SIGNA = "key_data_signa";
    public static final String KEY_DATA_FIELD55 = "key_data_field55";
    public static final String KEY_SIGNA_TRACE = "key_signa_trace";
    private Map<String, Object> mapSigna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideLeftKeepMargin();
        setHint(getString(R.string.upload_signature));
        start();
        path = FlowControl.MapHelper.getSignPath();
        if (!path.isEmpty()) {
            Log.i(TAG, "保存图片的路径不为空。。。");
            //开始上送电子签名
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap smallBitmap = bitmap2.smallBitmap(bitmap);
            boolean saveBitmapTopbm = bitmap2.saveBitmapTopbm(smallBitmap);
            if (saveBitmapTopbm) {
                String path1 = SaveBitmap.SDPATH + "/sign/pbmFile/" + "2.jbig";
                try {
                    byte[] byteArray = toByteArray(path1);
                    String shuju = BCDHelper.bcdToString(byteArray);
                    Log.i("kxf", "图片数据：" + shuju);
                    Log.i("kxf", "图片数据大小：" + byteArray.length);
                    Log.i(TAG, "开始上送电子签名。。。");

                    net(byteArray);
//                                uploadElectronicSignature(order,byteArray);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                TLog.showToast("签名图片压缩失败，请重试！");
                Log.i("kxf", "图片保存失败");
            }
        } else {
            Log.i(TAG, "保存图片的路径为空。。。");
            TLog.showToast(getResources().getString(R.string.sign_save_fail));
        }
    }

    /**
     * 网络请求
     */
    private void net(final byte[] bs) {
        TLog.l("联机网络请求");
        mapSigna = new HashMap();
        final String trace = FlowControl.MapHelper.getSerial();
        String field55 = null;
        try {
            field55 = packagefield55(trace);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapSigna.put(KEY_DATA_FIELD55, field55);
        mapSigna.put(KEY_DATA_SIGNA, bs);
        mapSigna.put(KEY_SIGNA_TRACE, trace);
        int action = ActionConstant.ACTION_UPLOAD_SIGNA;
        saveSigna(trace, field55, bs, "n");
        NetHelper.distribution(action, mapSigna, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                TLog.l("签名上送成功！");
                saveSigna(trace, null, null, "s");
                TLog.showToast("签名上送成功！");
                startNextFlow();
                finish();
            }

            @Override
            public void onFailure(int code, final String s) {
                NetUploadSignaWaitAty.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TLog.l(s);
                        saveSigna(trace, null, null, "f");
                        startNextFlow();
                        finish();
                    }
                });
            }
        });
    }

    protected void clickLeft() {
    }

    /**
     * 上送签名，转化为数组
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {
        Log.i(TAG, "toByteArray()...filename=" + filename);
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    private String packagefield55(String trace) throws Exception {
        Log.i(TAG, "packagefield55(String trace)...");
        Log.i(TAG, "trace=" + trace);
        TransactionData t = TransactionDataDao.getTranByTraceNO(trace);
        int action = t.getAction();
        String n = "";
        String n1 = "FF00";
        String n2 = SettingConstant.getMERCHANT_NAME();
        byte[] bytes1 = n2.getBytes("gbk");
        n2 = BCDHelper.bcdToString(bytes1);
        String n3 = Integer.toHexString(n2.length() / 2);
        if (n3.length() < 2) {
            n3 = "0" + n3;
        }
        n = n1 + n3 + n2;
        n1 = "FF01";
        n2 = getString(ActionConstant.getAction(action));
        Log.i(TAG + " kxf", "交易类型 n2=" + n2);
        byte[] bytes2 = n2.getBytes("gbk");
        n2 = BCDHelper.bcdToString(bytes2);
        n3 = Integer.toHexString(n2.length() / 2);
        if (n3.length() < 2) {
            n3 = "0" + n3;
        }
        n = n + n1 + n3 + n2;
        n1 = "FF02";
        n2 = "01";
        n3 = Integer.toHexString(n2.length() / 2);
        if (n3.length() < 2) {
            n3 = "0" + n3;
        }
        n = n + n1 + n3 + n2;

        n1 = "FF06";
        n2 = t.getYear() + t.getDate() + t.getTime();
        Log.i(TAG + " kxf", "n2=" + n2);
        n3 = Integer.toHexString(n2.length() / 2);
        if (n3.length() < 2) {
            n3 = "0" + n3;
        }
        n = n + n1 + n3 + n2;

        n1 = "FF0A";
        n2 = "156";
        byte[] bytes = n2.getBytes();
        n2 = BCDHelper.bcdToString(bytes);
        n3 = Integer.toHexString(3);
        if (n3.length() < 2) {
            n3 = "0" + n3;
        }
        n = n + n1 + "03" + n2;

//        n=n+"FF300A50424F43204445424954"+"FF310A50424F43204445424954"+"FF2208A000000333010101"+"FF23081DF54F39C90A0938";

        return n + "FF700102";
    }

    /**
     * * 保存上传的状态和数据
     *
     * @param trace
     * @param signa55
     * @param bs      电子签名数据
     * @param state   电子签名数据上送状态, 'n'未上送,'s'已上送,'f'失败
     */
    private void saveSigna(@NonNull String trace, String signa55, byte[] bs, @NonNull String state) {
        List<KeyValue> ls = new ArrayList<>();
        KeyValue[] kvs = null;
        ls.add(new KeyValue("signDataStatus", state));
        if (null != bs) {
            ls.add(new KeyValue("signData", bs));
        }
        if (null != signa55) {
            ls.add(new KeyValue("signICData", signa55));
        }
        kvs = ls.toArray(new KeyValue[ls.size()]);
        try {
            TransactionDataDao.updateByTrace(trace, kvs);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}