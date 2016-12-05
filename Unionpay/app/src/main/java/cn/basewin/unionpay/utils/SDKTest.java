package cn.basewin.unionpay.utils;

import android.util.Log;

import com.basewin.services.ServiceManager;
import com.basewin.utils.CUPParam;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/8 14:02<br>
 * 描述:  <br>
 */
public class SDKTest {
    private static final String TAG = SDKTest.class.getName();

    public static boolean LoadKey() throws Exception {
        try {
            StringBuffer sb = new StringBuffer("");
            boolean ret;
            ret = ServiceManager.getInstence().getPinpad().format();
            Log.e(TAG, "format :" + ret + "\n");
            boolean bRet;
            Log.e(TAG, "aid_data.length :" + CUPParam.aid_data.length + "\n");
            for (int j = 0; j < CUPParam.aid_data.length; j++) {
                bRet = ServiceManager.getInstence().getPboc().updateAID(0, CUPParam.aid_data[j]);
                Log.e(TAG, "下载 aid" + j + " bRet = " + bRet + ":" + CUPParam.aid_data[j]);
            }
            Log.e(TAG, "a_data.length :" + CUPParam.ca_data.length + "\n");
            for (int i = 0; i < CUPParam.ca_data.length; i++) {
                bRet = ServiceManager.getInstence().getPboc().updateRID(0, CUPParam.ca_data[i]);
                Log.e(TAG, "下载 rid" + i + " bRet = " + bRet + ":" + CUPParam.ca_data[i]);
            }
            String ridString = "9F0605A000000003DF2801019F220101DF050420091231DF060101DF070101DF028180C696034213D7D8546984579D1D0F0EA519CFF8DEFFC429354CF3A871A6F7183F1228DA5C7470C055387100CB935A712C4E2864DF5D64BA93FE7E63E71F25B1E5F5298575EBE1C63AA617706917911DC2A75AC28B251C7EF40F2365912490B939BCA2124A30A28F54402C34AECA331AB67E1E79B285DD5771B5D9FF79EA630B75DF040103DF0314D34A6A776011C7E7CE3AEC5F03AD2F8CFC5503CCDF25080420081014214014";
            bRet = ServiceManager.getInstence().getPboc().updateRID(0, ridString);
            Log.e(TAG, "载入RID " + bRet);

            ret = ServiceManager.getInstence().getPinpad().loadMainKey("31313131313131313232323232323232");
            Log.e(TAG, "loadMainKey :" + ret + "\n");
//            ret = ServiceManager.getInstence().getPinpad().loadMacKey("F40379AB9E0EC533F40379AB9E0EC533", "82E13665");
//            Log.e(TAG, "loadMacKey :" + ret + "\n");
//            ret = ServiceManager.getInstence().getPinpad().loadPinKey("F40379AB9E0EC533F40379AB9E0EC533", "82E13665");
//            Log.e(TAG, "loadPinKey :" + ret + "\n");
//            ret = ServiceManager.getInstence().getPinpad().loadTDKey("F40379AB9E0EC533F40379AB9E0EC533", "82E13665");
//            Log.e(TAG, "loadTDKey :" + ret + "\n");
            // data =

//            String retuen = ServiceManager.getInstence().getPinpad().encryptData(KeyType.MAC_KEY, "1111111111111111");
//            Log.e(TAG, "加载 状态：" + retuen);
//            retuen = ServiceManager.getInstence().getPinpad().encryptData(KeyType.PIN_KEY, "1111111111111111");
//            Log.e(TAG, "加载 状态：" + retuen);
//            retuen = ServiceManager.getInstence().getPinpad().encryptData(KeyType.TRACK_KEY, "1111111111111111");
//            Log.e(TAG, "加载 状态：" + retuen);
//            Log.e(TAG, sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "---------------------------------------------------");
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
