package cn.basewin.unionpay.utils;

import android.os.RemoteException;
import android.util.Log;

import com.basewin.define.BwPinpadSource;
import com.basewin.define.KeyType;
import com.basewin.services.PBOCBinder;
import com.basewin.services.PinpadBinder;
import com.basewin.services.ServiceManager;
import com.pos.sdk.security.PosSecurityManager;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import cn.basewin.unionpay.menu.action.MenuPosSignOut;

/**
 * 该类实现交易过程中各种加解密算法
 *
 * @author mqiu
 */
public class TradeEncUtil {

    private static final String TAG = "TradeEncUtil";

    static String DES = "DES/ECB/NoPadding";
    static String TriDes = "DESede/ECB/NoPadding";

    private static PinpadBinder mPinPadService = null;
    private static PBOCBinder mPbocService = null;

    static {

        try {
            mPinPadService = ServiceManager.getInstence().getPinpad();
            mPbocService = ServiceManager.getInstence().getPboc();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static byte[] getMacStandard(byte[] data) {

        byte[] temp = null;
        String result = null;

        try {
            if (data.length % 8 != 0) {
                temp = new byte[(data.length / 8 + 1) * 8];
                System.arraycopy(data, 0, temp, 0, data.length);
            } else {
                temp = data;
            }

            byte[] xor = new byte[8];
            for (int i = 0; i < (temp.length / 8); i++) {
                for (int j = 0; j < 8; j++) {
                    xor[j] = (byte) (((byte) xor[j] & 0xff) ^ ((byte) temp[i
                            * 8 + j] & 0xff));
                }
            }

            byte[] last = BCDHelper.bcdToString(xor).getBytes();
            byte[] left = new byte[8];
            byte[] right = new byte[8];

            System.arraycopy(last, 0, left, 0, 8);
            System.arraycopy(last, 8, right, 0, 8);

            // temp = des_crypt(key, left);
            temp = BCDHelper.stringToBcd(mPinPadService.encryptData(
                    KeyType.MAC_KEY, BCDHelper.bcdToString(left)));

            for (int j = 0; j < 8; j++) {
                xor[j] = (byte) (((byte) temp[j] & 0xff) ^ ((byte) right[j] & 0xff));
            }

            temp = BCDHelper.stringToBcd(mPinPadService.encryptData(
                    KeyType.MAC_KEY, BCDHelper.bcdToString(xor)));
            Log.e(TAG, "getMacPBOCDes:temp="
                    + BCDHelper.bcdToString(temp));

            result = BCDHelper.bcdToString(temp, 0, 4);
            Log.e(TAG, "getMacPBOCDes:"
                    + BCDHelper.bcdToString(result.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result.getBytes();
    }

    public static String getEncTrack3Standard(String data) {

        Log.i(TAG, "getEncTrack3Standard:" + data);
        boolean flag = false;

        if (data == null || data.length() == 0) {
            return null;
        }

        if (data.length() % 2 != 0) {    // 奇数补f
            data += 'F';
            flag = true;
        }

        byte[] input = BCDHelper.stringToBcd(data);
        byte[] tdb3 = new byte[8];

        Log.i(TAG, "TDB3:" + tdb3);

        System.arraycopy(input, input.length - 9, tdb3, 0, 8);

        String enc = null;

        try {
            enc = mPinPadService.encryptData(KeyType.TRACK_KEY, BCDHelper.bcdToString(tdb3));
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.arraycopy(BCDHelper.stringToBcd(enc), 0, input, input.length - 9, 8);

        System.out.println("磁道三加密结果 " + BCDHelper.bcdToString(input));

        String result = BCDHelper.bcdToString(input);
        if (flag) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static String getEncTrack2Standard(String data) {

        Log.i(TAG, "getEncTrack2Standard:" + data);

        boolean flag = false;

        if (data == null || data.length() == 0) {
            return null;
        }

        if (data.length() % 2 != 0) {    // 奇数补0
            flag = true;
            data += '0';
        }


        byte[] input = BCDHelper.stringToBcd(data);
        byte[] tdb2 = new byte[8];

        Log.i(TAG, "TDB2:" + tdb2);

        System.arraycopy(input, input.length - 9, tdb2, 0, 8);

        String enc = null;

        try {
            enc = mPinPadService.encryptData(KeyType.TRACK_KEY, BCDHelper.bcdToString(tdb2));
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.arraycopy(BCDHelper.stringToBcd(enc), 0, input, input.length - 9, 8);

        System.out.println("磁道二加密结果： " + BCDHelper.bcdToString(input));

        String result = BCDHelper.bcdToString(input);
        if (flag) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    public static String getMacECB(byte[] data) throws RemoteException {
        String result = null;
        result = mPinPadService.calcMAC(
                BCDHelper.bcdToString(data, 0, data.length), BwPinpadSource.MAC_ECB);
        Log.i(TAG, "硬件MAC结果：" + result);
        return result;
    }


    //3DES加密
    public static byte[] TDesEnc(byte[] key, byte[] in) throws Exception {

        Key deskey = null;
        byte[] tdesKey = new byte[24];
        if (key.length % 8 != 0)
            return null;

        if (key.length == 8) {
            System.arraycopy(key, 0, tdesKey, 0, 8);
            System.arraycopy(key, 0, tdesKey, 8, 8);
            System.arraycopy(key, 0, tdesKey, 16, 8);
        }

        if (key.length == 16) {
            System.arraycopy(key, 0, tdesKey, 0, 16);
            System.arraycopy(key, 0, tdesKey, 16, 8);
        }

        DESedeKeySpec spec = new DESedeKeySpec(tdesKey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(in);

        return bOut;
    }

    //3DES解密
    public static byte[] TDesDec(byte[] key, byte[] in) throws Exception {

        Key deskey = null;
        byte[] tdesKey = new byte[24];
        if (key.length % 8 != 0)
            return null;

        if (key.length == 8) {
            System.arraycopy(key, 0, tdesKey, 0, 8);
            System.arraycopy(key, 0, tdesKey, 8, 8);
            System.arraycopy(key, 0, tdesKey, 16, 8);
        }

        if (key.length == 16) {
            System.arraycopy(key, 0, tdesKey, 0, 16);
            System.arraycopy(key, 0, tdesKey, 16, 8);
        }

        DESedeKeySpec spec = new DESedeKeySpec(tdesKey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(in);

        return bOut;
    }

    //DES解密
    public static byte[] des_encrypt(byte key[], byte data[]) {

        try {
            KeySpec ks = new DESKeySpec(key);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);

            Cipher c = Cipher.getInstance(DES);
            c.init(Cipher.ENCRYPT_MODE, ky);
            return c.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //DES加密
    public static byte[] des_decrypt(byte key[], byte data[]) {

        try {
            KeySpec ks = new DESKeySpec(key);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);

            Cipher c = Cipher.getInstance(DES);
            c.init(Cipher.DECRYPT_MODE, ky);
            return c.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //联机签到成功更新工作密钥
    public static boolean updateWorkKey(String data) {
        Log.e("------!!!","Here！！");
        if (data == null || (data.length() != 48 && data.length() != 80 && data.length() != 112 && data.length() != 120 && data.length() != 168)) {
            Log.e(TAG, "返回的工作密钥长度不对！");
            return false;
        }
        Log.i(TAG, "data[" + data.length() + "]=" + data);
        boolean dataPin = false;
        boolean dataMac = false;
        boolean dataTdk = false;
        try {
            Log.d(TAG, "data.substring(0, 32)=" + data.substring(0, 32));
            Log.d(TAG, "data.substring(32, 40)=" + data.substring(32, 40));
            Log.d(TAG, "data.substring(40, 56)=" + data.substring(40, 56));
            Log.d(TAG, "data.substring(72, 80)=" + data.substring(72, 80));
//            Log.d(TAG, "data.substring(80, 112)=" + data.substring(80, 112));
//            Log.d(TAG, "data.substring(112, 120)=" + data.substring(112, 120));
            //先默认成功
            dataPin = mPinPadService.loadPinKey(data.substring(0, 32),
                    data.substring(32, 40));
            dataMac = mPinPadService.loadMacKey(data.substring(40, 56),
                    data.substring(72, 80));
//            dataPin = true;
//            dataMac = true;


//            dataTdk = mPinPadService.loadTDKey(data.substring(80, 112),
//                    data.substring(112, 120));
            dataTdk = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "dataPin=" + dataPin);
        Log.d(TAG, "dataMac=" + dataMac);
        Log.d(TAG, "dataTdk=" + dataTdk);

        if (dataPin && dataMac && dataTdk) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 更新主密钥
     *
     * @param key
     * @return
     */
    public static boolean loadMainKey(String key) {
        TLog.l("loadMainKey(String key)  开始写入。。。");
        boolean b = false;
        try {
            b = mPinPadService.loadMainKey(key);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        MenuPosSignOut.signOutPos();//主密钥已更新，用户需要重新签到
        if (b) {
            TLog.l("写入成功。。。");
            PosSecurityManager.getDefault().SysSetWriteKeyResult(0);
        } else {
            TLog.e(TAG, "写入失败。。。");
            PosSecurityManager.getDefault().SysSetWriteKeyResult(2);
        }
        return b;
    }
}