package cn.basewin.unionpay.db;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.utils.IDUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 10:04<br>
 * 描述:  <br>
 */
public class TransactionDataDao {
    private static final String TAG = "TransactionDataDao";

    //拿到最后一笔数据判断是否冲正
    public static List<TransactionData> selectAll() throws DbException {
        return AppContext.db().selector(TransactionData.class).findAll();
    }

    /**
     * 查找所有有效的交易
     *
     * @return
     * @throws DbException
     */
    public static List<TransactionData> selectAllValid() throws DbException {
        return AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").findAll();
    }

    /**
     * 查找所有无效的交易
     *
     * @return
     * @throws DbException
     */
    public static List<TransactionData> selectAllNotValid() throws DbException {
        return AppContext.db().selector(TransactionData.class).where("status", "!=", "-").and("status", "!=", "+").findAll();
    }

    /**
     * 查找指定的有效的交易
     *
     * @return
     * @throws DbException
     */
    public static TransactionData selectValid(WhereBuilder wb) throws DbException {
        TransactionData td = AppContext.db().selector(TransactionData.class).where(wb).findFirst();
        if (td != null && td.getStatus() != null && ("-".equals(td.getStatus()) || "+".equals(td.getStatus()))) {
            return td;
        } else {
            return null;
        }
    }

    /**
     * 查找指定的有效的交易
     *
     * @return
     * @throws DbException
     */
    public static List<TransactionData> selectValid(WhereBuilder wb, String order) throws DbException {
        List<TransactionData> tdsv;
        List<TransactionData> tds = AppContext.db().selector(TransactionData.class).where(wb).orderBy(order, true).findAll();
        if (tds == null || tds.size() < 1) {
            return null;
        }
        tdsv = new ArrayList<>();
        for (TransactionData td : tds) {
            if (td != null && td.getStatus() != null && ("-".equals(td.getStatus()) || "+".equals(td.getStatus()))) {
                tdsv.add(td);
            }
        }
        if (tdsv.size() > 0) {
            return tdsv;
        } else {
            return null;
        }
    }

    /**
     * 查找最后一笔有效的交易
     *
     * @return
     * @throws DbException
     */
    public static TransactionData selectLastValid() throws DbException {
        return AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").orderBy("id", true).findFirst();
    }

    /**
     * 查找最后一笔有效的交易
     *
     * @return
     * @throws DbException
     */
    public static TransactionData selectLastNeedPrint() throws DbException {
        return AppContext.db().selector(TransactionData.class).where("needPrint", "=", true).orderBy("id", true).findFirst();
    }

    /**
     * 查找所有成功的交易(成功交易不包括查询、预授权、预授权撤销、
     * 自动冲正交易，管理类交易。 不成功的联机类交易不做批上送。 )
     *
     * @return
     * @throws DbException
     */
    public static List<TransactionData> selectAllSuccess() throws DbException {
        List<TransactionData> ls = AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").findAll();
        List<TransactionData> tds = null;
        if (ls != null && ls.size() > 0) {
            tds = new ArrayList<TransactionData>();
            for (TransactionData td : ls) {
                if (isSuccessTrace(td.getName())) {
                    tds.add(td);
                }
            }
        }
        return tds;
    }

    public static List<TransactionData> selectAllSuccessByCardType(String cardType) throws DbException {
        List<TransactionData> ls = AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").findAll();
        List<TransactionData> tds = null;
        if (ls != null && ls.size() > 0) {
            tds = new ArrayList<TransactionData>();
            for (TransactionData td : ls) {
                if (isSuccessTrace(td.getName()) && cardType.equals(td.getCardType())) {
                    tds.add(td);
                }
            }
        }
        return tds;
    }

    public static List<TransactionData> selectAllValidIC() throws DbException {
        List<TransactionData> ls = AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").findAll();
        List<TransactionData> tds = null;
        if (ls != null && ls.size() > 0) {
            tds = new ArrayList<TransactionData>();
            for (TransactionData td : ls) {
                if (null != td.getServiceCode() && td.getServiceCode().length() > 1 && "05".equals(td.getServiceCode().substring(0, 2))) {
                    tds.add(td);
                }
            }
        }
        return tds;
    }

    public static boolean isSuccessTrace(String name) {
        int[] actions = new int[]{ActionConstant.ACTION_AUTH,
                ActionConstant.ACTION_CANCEL, ActionConstant.ACTION_QUERY_BALANCE};
        for (int i : actions) {
            if (equalsAction(i, name)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsAction(int action, String name) {
        return AppContext.getInstance().getApplicationContext().getString(ActionConstant.getAction(action)).equals(name);
    }

    public static List<TransactionData> selectAllOrder(String order) throws DbException {
        return AppContext.db().selector(TransactionData.class).orderBy(order, true).findAll();
    }

    /**
     * 查找所有有效的交易（包括已经撤销的交易）
     *
     * @param order
     * @return
     * @throws DbException
     */
    public static List<TransactionData> selectAllOrderValid(String order) throws DbException {
        return AppContext.db().selector(TransactionData.class).where("status", "=", "-").or("status", "=", "+").orderBy(order, true).findAll();
    }

    public static List<TransactionData> select(WhereBuilder wb, String order) throws DbException {
        return AppContext.db().selector(TransactionData.class).where(wb).orderBy(order, true).findAll();
    }

    public static List<TransactionData> select(WhereBuilder wb) throws DbException {
        return AppContext.db().selector(TransactionData.class).where(wb).findAll();
    }

    /**
     * 通过流水号获取数据
     *
     * @param msg
     * @return
     * @throws DbException
     */
    public static TransactionData selectByTrace(String msg) throws DbException {
        return AppContext.db().selector(TransactionData.class).where("trace", "=", msg).findFirst();
    }

    public static void update(TransactionData data) {

    }

    public static void update(WhereBuilder wb, KeyValue... kvs) throws DbException {
        AppContext.db().update(TransactionData.class, wb, kvs);
    }

    public static void insert(TransactionData data) throws DbException {
        AppContext.db().save(data);
    }

    public static void updateByTrace(String trace, KeyValue... kvs) throws DbException {
        WhereBuilder wb = WhereBuilder.b("trace", "=", trace);
        AppContext.db().update(TransactionData.class, wb, kvs);
    }

    public static void delete(TransactionData data) {

    }

    public static void saveBeforeSend() throws DbException {
        saveBeforeSend(null);
    }

    public static void saveBeforeSend(@NonNull TransactionData td) throws DbException {
        Log.d(TAG, "saveBeforeSend start...");
        String batch = FlowControl.MapHelper.getBatch();
        if (batch != null || (!"".equals(batch))) {
            td.setBatch(batch);
        }

        String trace = FlowControl.MapHelper.getSerial();
        if (trace != null || (!"".equals(trace))) {
            td.setTrace(trace);
        }

        String amount = FlowControl.MapHelper.getMoney();
        if (amount != null || (!"".equals(amount))) {
            td.setAmount(amount);
        }

        Card card = FlowControl.MapHelper.getCard();
        if (card != null) {
            String _22 = PosUtil._22(card);
            if (_22 != null || (!"".equals(_22))) {
                td.setServiceCode(_22);
            }

            String expDate = card.getExpDate();
            if (expDate != null || (!"".equals(expDate))) {
                td.setExpDate(expDate);
            }

            String cardSn = card.get23();
            if (cardSn != null || (!"".equals(cardSn))) {
                td.setCardSn(cardSn);
            }

            String track2 = card.getTrack2ToD();
            if (track2 != null || (!"".equals(track2))) {
                td.setTrack2(track2);
            }

            String track3 = card.getTrack3ToD();
            if (track3 != null || (!"".equals(track3))) {
                td.setTrack3(track3);
            }

            String field55 = card.get55();
            if (field55 != null || (!"".equals(field55))) {
                td.setField55(field55);
            }
        }

        AppContext.db().save(td);
        Log.d(TAG, "saveBeforeSend end...");
    }

    public static void deleteAll() throws DbException {
        AppContext.db().dropTable(TransactionData.class);
    }

    public static void deleteById(TransactionData data, String trace) {

    }

    /**
     * 存储数据库
     *
     * @param iso
     */
    public static void save(Iso8583Manager iso) {
        Log.i(TAG, "save db...");
        TransactionData transactionData = null;
        Card card = FlowControl.MapHelper.getCard();
        String pan;
        String expDate;
        String _55 = "";
        if (card != null) {
            pan = card.getPan();
            expDate = card.getExpDate();
            _55 = card.get55();
        } else {
            pan = FlowControl.MapHelper.getCardNO();
            expDate = FlowControl.MapHelper.getCardExpDate();//卡有效期
        }
        try {
            transactionData = TransactionDataDao.selectByTrace(iso.getBit(11));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (transactionData == null) {
            transactionData = new TransactionData();
        }
        boolean result = "00".equals(iso.getBit(39));
        int action = FlowControl.MapHelper.getAction();
        transactionData.setAction(action);
        transactionData.setTrace(iso.getBit(11));//流水号
        transactionData.setBatch(SettingConstant.getBatch());//批次号
        String name = AppContext.getInstance().getString(ActionConstant.getAction(action));
        transactionData.setName(name);//交易名称
        transactionData.setEngName(ActionConstant.getAction(action, false));
        transactionData.setPan(pan);
        transactionData.setExpDate(expDate);//卡有效期
        if (!result) {
            transactionData.setField55(_55);
        }

        transactionData.setAmount(iso.getBit(4));//交易金额
        if (result) {
            transactionData.setSettleDate(iso.getBit(15));//结算日期
        }
        String field22 = iso.getBit(22);
        Log.i(TAG, "kxf debug");
        if (field22 != null && ("01".equals(field22.substring(0, 2)) || "02".equals(field22.substring(0, 2)) || "05".equals(field22.substring(0, 2)) || "07".equals(field22.substring(0, 2)))) {
            Log.i(TAG, "kxf field22=" + field22);
            transactionData.setServiceCode(field22);
        }
        String s1 = FlowControl.MapHelper.getGoodsCode();
        TLog.l("data 商品代码：" + s1);
        transactionData.setProduct(s1 == null ? "" : s1);//商品代码
        if (!result) {
            transactionData.setCardSn(iso.getBit(23));//卡序列号
            transactionData.setCurrency(iso.getBit(49));//货币代码
            transactionData.setTrack2(iso.getBit(35));//二磁道
            transactionData.setTrack3(iso.getBit(36));//三磁道
            transactionData.setField61(iso.getBit(61));
            transactionData.setField63(iso.getBit(63));
        }
        if (result) {
            transactionData.setField53(iso.getBit(53));
            transactionData.setDate(iso.getBit(13));//交易日期
            transactionData.setTime(iso.getBit(12));//交易时间
        }
        transactionData.setYear(TDevice.getYear());//年份
        if (result) {
            transactionData.setAuthCode(iso.getBit(38));//授权码
        }
        if (result) {
            String[] s = ActionConstant.getStatusByAction();
            transactionData.setStatus(s[0]);
            transactionData.setSettleType(s[1]);
            transactionData.setNeedPrint((Boolean) FlowControl.MapHelper.getMap().get(PrintWaitAty.THIS_TRACE_IS_NEED_PRINT));//设置打印标志位
        } else {
            transactionData.setStatus("");
            transactionData.setSettleType("");
        }
        String field63 = iso.getBit(63);
        if (field63 != null && field63.length() >= 3) {
            Log.i(TAG, "field63=" + field63);
            if ("cup".equalsIgnoreCase(field63.substring(0, 3)) || "000".equals(field63.substring(0, 3))) {
                transactionData.setCardType("1");
            } else {
                transactionData.setCardType("2");
            }
        }

        String field44 = iso.getBit(44);
        if (field44 != null && field44.length() > 0) {
            Log.i(TAG, "接收机构和收单机构 field44=" + field44);
//            transactionData.setAcqId(field44.substring(0, 11));//收单行ID
//            transactionData.setIssuerId(field44.substring(11, 22));//发卡行ID
            transactionData.setAcqId("");//收卡行ID
            transactionData.setIssuerId("");//发卡行ID
        } else {
            transactionData.setAcqId("");//收卡行ID
            transactionData.setIssuerId("");//发卡行ID
        }
        if (result) {
            transactionData.setReferenceNo(iso.getBit(37));//参考号
        }
        transactionData.setSignDataStatus("");//电子签名数据
        transactionData.setBalance("");//余额
        transactionData.setOperator(SettingConstant.getOPERATOR_NO());//操作员
        if (IDUtil.hasOLDTRACE(action)) {
            transactionData.setOldTrace(FlowControl.MapHelper.getTrace());//原凭证号
        }
        if (IDUtil.isREFUND(action)) {
            transactionData.setOldDate(FlowControl.MapHelper.getDate());//原交易日期
            transactionData.setOldReference(FlowControl.MapHelper.getReferNo());//原交易参考号
        }
        if (IDUtil.hasOLDAUTHCODE(action)) {
            transactionData.setOldAuthCode(FlowControl.MapHelper.getAuthCode());//原授权码
        }
        try {
            DbManager db = AppContext.db();
            db.saveOrUpdate(transactionData);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "save db success...");
    }

    /**
     * 存电子签名
     *
     * @param bitmap
     */
    public static void saveSignature(Bitmap bitmap) {
        String trace = FlowControl.MapHelper.getSerial();
        if (trace == null || "".equals(trace)) {
            return;
        }
        DbManager db = AppContext.db();
        try {
            TransactionData transactionData = db.selector(TransactionData.class).where("trace", "=", "trace").findFirst();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            transactionData.setSignData(baos.toByteArray());
            db.update(transactionData, "signData");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过参考号 拿到 交易数据
     *
     * @param trace
     */
    public static TransactionData getTranByTraceNO(String trace) throws DbException {
        TransactionData referenceNo = db().selector(TransactionData.class).where("trace", "=", trace).findFirst();
        return referenceNo;
    }

    public static DbManager db() {
        return AppContext.db();
    }

    /**
     * 打印数据库中的所有数据
     */
    public static void showDbAll() {
        List<TransactionData> tds = null;
        try {
            tds = selectAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        showDb(tds);
    }

    public static void showDb(List<TransactionData> tds) {
        Log.e(TAG, "-------------------------showDb start---------------------");
        if (tds != null && tds.size() > 0) {
            for (TransactionData td : tds) {
                Log.i(TAG, "" + td);
            }
        }
        Log.e(TAG, "-------------------------showDb end---------------------");
    }
}
