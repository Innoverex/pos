package cn.basewin.unionpay.network.remote;

import android.os.RemoteException;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ErrorConstant;
import cn.basewin.unionpay.db.ResponseCodeDao;
import cn.basewin.unionpay.setting.CommuParmOtherAyt;
import cn.basewin.unionpay.setting.MerchantSetting;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/25 09:51<br>
 * 描述: 普通的报文，比如说签到 和初始化机具等报文 <br>
 */
public abstract class OrdinaryMessage extends NetMessage {
    @Override
    public boolean checkMAC(Iso8583Manager isoMessage) {
        byte[] macInput = new byte[0];
        try {
            macInput = isoMessage.getMacData("msgid", "63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String calcMAC = null;
        try {
            calcMAC = TradeEncUtil.getMacECB(macInput);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String oldMAC = BCDHelper.bcdToString(isoMessage.getBitBytes(64));
        return (oldMAC.equals(calcMAC));
    }

    @Override
    protected boolean IsMAC() {
        return false;
    }


    @Override
    protected Iso8583Manager getBeforEncryption(Map<String, Object> map) throws Exception {
        Iso8583Manager packManager = new Iso8583Manager(context());
        packManager.setBit("tpdu", CommuParmOtherAyt.getTPDU());
        packManager.setBit("header", SettingConstant.getHEADER());
        //初始化机具等报文不需要11域
//        packManager.setBit(11, SettingConstant.getTraceAuto());
        packManager.setBit(41, MerchantSetting.getTerminalNo());
        packManager.setBit(42, MerchantSetting.getMerchantNo());
        return packManager;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        if (!Reverse.checkMsg(msg8583Data, iso)) {
            TLog.l("校验数据失败");
            lis.onFailure(ErrorConstant.NET_CHECK_MAC, ResponseCodeDao.show("A0"));
            return false;
        }
        if (null != iso.getBit(39) && !iso.getBit(39).equals("00")) {
            TLog.l("39域不为00");
            lis.onFailure(ErrorConstant.NET_CHECK_MAC, ResponseCodeDao.show(iso.getBit(39)));
            return false;
        }
        return true;
    }

    @Override
    public void errorNet(int code, String msg) {

    }
}
