package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.ChoosePayTypeAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.FormatUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/15 14:26<br>
 * 描述：分期消费
 */
@AnnotationNet(action = ActionConstant.ACTION_INSTALLMENT)
public class NetInstallment extends ReverseTradeMessage {
    private static final String TAG = NetInstallment.class.getName();
    /**
     * 首期还款金额
     */
    public static final String KEY_FIRSTPAYMENT = "first_payment";
    /**
     * 持卡人分期付款手续费
     */
    public static final String KEY_HANDLING_CHARGE = "handling_charge";
    /**
     * 手续费支付方式
     */
    public static final String KEY_HANDLING_TYPE = "handling_type";
    /**
     * 首期手续费
     */
    public static final String KEY_FIRST_HANDLING_CHARGE = "first_handling_charge";
    /**
     * 每期手续费
     */
    public static final String KEY_EVERY_HANDLING_CHARGE = "every_handling_charge";

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0200");
        packManager.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);

        String _22 = PosUtil._22(card);
        packManager.setBit(22, _22);

        packManager.setBit(25, "64");
        packManager.setBit(26, "12");

        if (card != null) {
            packManager.setBit(14, card.getExpDate());
        }
        if (!card.isIC()) {
            isoSetTrack3(iso, card.getTrack3ToD());
        } else {
            packManager.setBit(23, card.get23());
            packManager.setBit(55, card.get55());
        }

        isoSetTrack2(iso, card.getTrack2ToD());
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            packManager.setBinaryBit(52, com.basewin.utils.BCDHelper.StrToBCD(new String(pin)));
            Log.i(TAG, "PIN from pinpad：" + new String(pin));
        }
        packManager.setBit(49, "156");
        packManager.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);

        String period = FlowControl.MapHelper.getInstallNO();//分期期数
        String productId = FlowControl.MapHelper.getProductId();//项目编号
        String type = (String) map.get(ChoosePayTypeAty.KEY_CHOOSE_PAY_TYPE);//手续费支付方式
        String typeId = type.equals("一次性支付") ? "0" : "1";//支付方式
        String bit62 = FormatUtil.alignRigthFillZero(period, 2) + FormatUtil.alignLeftFillSpace(productId, 30)
                + FormatUtil.alignLeftFillSpace("1" + typeId, 30);
        String bit62Length = FormatUtil.alignRigthFillZero(bit62.length() + "", 3);
        packManager.setBit(60, "22" + batchAuto + "000" + "6" + "0");
        packManager.setBinaryBit(62, BytesUtil.ascii2Bcd(bit62Length + bit62));
        packManager.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        packManager.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        String bit62 = new String(iso.getBitBytes(62));
        if (super.afterRequest(iso, lis)) {
            double firstPayment = Double.parseDouble(bit62.substring(0, 12));//首期还款金额
            int payType = Integer.parseInt(bit62.substring(12, 15));//还款币种
            double handlingCharge = Double.parseDouble(bit62.substring(15, 27));//持卡人分期付款手续费

            FlowControl.MapHelper.getMap().put(NetInstallment.KEY_FIRSTPAYMENT, firstPayment + "");
            FlowControl.MapHelper.getMap().put(NetInstallment.KEY_HANDLING_CHARGE, handlingCharge + "");
            int integralAward = 0;
            int handlingType = 1;
            double firstHandlingCharge = 0;
            double everyHandlingCharge = 0;
            if (handlingCharge != 0) {
                integralAward = Integer.parseInt(bit62.substring(27, 39));//分期付款奖励积分
                handlingType = Integer.parseInt(bit62.substring(39, 40));//手续费支付方式
                firstHandlingCharge = Double.parseDouble(bit62.substring(40, 52));//首期手续费
                everyHandlingCharge = Double.parseDouble(bit62.substring(52, 64));//每期手续费
                FlowControl.MapHelper.getMap().put(NetInstallment.KEY_HANDLING_TYPE, handlingType == 0 ? "一次性支付手续费" : "分期支付手续费");
            }
            FlowControl.MapHelper.getMap().put(NetInstallment.KEY_FIRST_HANDLING_CHARGE, firstHandlingCharge + "");
            FlowControl.MapHelper.getMap().put(NetInstallment.KEY_EVERY_HANDLING_CHARGE, everyHandlingCharge + "");
            return true;
        } else {
            return false;
        }

    }
}
