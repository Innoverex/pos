package cn.basewin.unionpay.trade;

import android.util.Log;
import android.view.View;

import com.basewin.packet8583.factory.Iso8583Manager;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.SettlementUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.CustomInputDialog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * Created by kxf on 2016/8/4.
 * 结算的网络等待界面
 */
public class NetSettleWaitAty extends BaseSettleWaitAty {

    private static final String TAG = "NetSettleWaitAty";
    public static final String SETTLEMENT_RESULT = "settlement_result";
    public List<TransactionData> tdsS;//磁条卡交易
    public List<TransactionData> tds;//需要上传的所有成功的交易

    /**
     * 网络请求
     */
    @Override
    protected void net() {
        setHint(getString(R.string.settle_wait));
        if (SettlementUtil.getCurrentSettleState()) {
            initS(SettlementUtil.getCurrentSettleField48Result());
        } else {
            netStartSettle();
        }
    }

    private void netStartSettle() {
        final int intExtra = (int) FlowControl.MapHelper.getMap().get(FlowControl.KEY_ACTION);//FlowControl.KEY_ACTION
        Log.e(TAG, "网络请求 intExtra=" + getString(ActionConstant.getAction(intExtra)));
        NetHelper.distribution(intExtra, FlowControl.MapHelper.getMap(), new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                Log.e(TAG, "field39=" + data.getBit(39));
                String time = null;
                String date = null;
                time = data.getBit(12);
                date = data.getBit(13);
                String dateTime = TDevice.formatDate(date + time);
                Log.e(TAG, "dateTime=" + dateTime);
                String field48Result = data.getBit(48);
                if (field48Result == null || field48Result.length() != 62) {
                    final CustomInputDialog ciDialog = new CustomInputDialog(activity);
                    ciDialog.setRdMsg("结算失败！");
                    ciDialog.setRdLeftButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ciDialog.dismiss();
                            finish();
                        }
                    });
                    ciDialog.setCancelable(false);
                    ciDialog.setCanceledOnTouchOutside(false);
                    ciDialog.show();
                    return;
                }
                SettlementUtil.setCurrentSettleState(true);
                SettlementUtil.setCurrentSettleField48Result(field48Result);
                initS(field48Result);
            }

            @Override
            public void onFailure(int code, final String s) {
                Log.e(TAG, "onFailure  " + s);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HintDialog hintDialog = DialogHelper.getHintDialog(activity);
                        hintDialog.setTextHint(s);
                        hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                            @Override
                            public void ok() {
                                activity.finish();
                            }

                            @Override
                            public void calcel() {
                                activity.finish();
                            }
                        });
                        hintDialog.show();
                    }
                });
            }
        });
    }

    protected void initS(String field48Result) {
        FlowControl.MapHelper.getMap().put("field48Result", field48Result);
        int d = Integer.parseInt(field48Result.substring(30, 31));
        int f = Integer.parseInt(field48Result.substring(61, 62));
        Log.i(TAG, "d=" + d + "  f=" + f);
        String strInfo = "内卡对账" + getAccountsState(d) + "，外卡对账" + getAccountsState(f);
        Log.e(TAG, "strInfo=" + strInfo);
        setHint(strInfo + "，" + getString(R.string.upload_settle_wait));
        Log.i(TAG, strInfo + ",开始批上送...");
        if (d <= 1 && f <= 1) {
            FlowControl.MapHelper.getMap().put(SETTLEMENT_RESULT, true);
            startNextFlow();
            finish();
        } else {
            FlowControl.MapHelper.getMap().put(SETTLEMENT_RESULT, false);
            if (d >= 2 && f >= 2) {
                try {
                    tds = TransactionDataDao.selectAllSuccess();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            } else if (d >= 2 && f < 2) {
                try {
                    tds = TransactionDataDao.selectAllSuccessByCardType("1");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            } else if (d < 2 && f >= 2) {
                try {
                    tds = TransactionDataDao.selectAllSuccessByCardType("2");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            if (tds != null && tds.size() > 0) {
                tdsS = new ArrayList<>();
                for (TransactionData td : tds) {
                    if (null != td.getServiceCode() && td.getServiceCode().length() > 1 && "02".equals(td.getServiceCode().substring(0, 2))) {
                        tdsS.add(td);
                    }
                }
                if (tdsS != null && tdsS.size() > 0) {
                    handlerUp.sendEmptyMessage(msg_s);
                    setHint(getString(R.string.upload_settle_s_wait));
                    return;
                }
            }
            startNextFlow();
            finish();
        }
    }

    /**
     * 上送磁条卡
     */
    protected void netS() {
        super.netS();
        String field48 = "";
        String field60_3;
        if (index >= tdsS.size()) {
            isEnd = true;
            field60_3 = "207";
            field48 = String.format("%04d", tdsS.size());
        } else {
            currentUpTds.clear();
            field60_3 = "201";
            if ((index + 8) < tdsS.size()) {
                field48 = String.format("%02d", 8);
                for (int i = 0; i < 8; i++) {
                    TransactionData td = tdsS.get(index + i);
                    currentUpTds.add(td);
                    field48 = field48 + formatField48(td);
                }
                currentNum = 8;
            } else {
                field48 = String.format("%02d", tdsS.size() - index);
                for (int i = index; i < tdsS.size(); i++) {
                    TransactionData td = tdsS.get(i);
                    currentUpTds.add(td);
                    field48 = field48 + formatField48(td);
                }
                currentNum = tdsS.size() - index;
            }
        }
        TLog.l("上送磁条卡 网络请求");
        HashMap<String, Object> map = new HashMap<>();
        Log.i(TAG, "field48=" + field48 + " ,  field60_3=" + field60_3);
        map.put("field48", field48);
        map.put("field60_3", field60_3);
        retryCount += 1;
        NetHelper.distribution(ActionConstant.ACTION_BATCH_UP_FINANCIAL_TRANS, map, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                Log.e(TAG, "NetSettleWaitAty onSuccess");
                retryCount = 0;
                index += currentNum;
                if (index >= tdsS.size() && isEnd) {
                    startNextFlow();
                    finish();
                } else {
                    handlerUp.sendEmptyMessage(msg_s);
                }
            }

            @Override
            public void onFailure(int code, String s) {
                if (retryCount < retryCountMax) {
                } else {
                    index += currentNum;
                }
                handlerUp.sendEmptyMessage(msg_s);
            }
        });
    }
}