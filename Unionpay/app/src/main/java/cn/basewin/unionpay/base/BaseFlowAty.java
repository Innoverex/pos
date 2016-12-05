package cn.basewin.unionpay.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.basewin.aidl.OnPBOCListener;
import com.basewin.define.OutputQPBOCResult;
import com.basewin.define.PBOCErrorCode;
import com.basewin.define.PBOCTransactionResult;
import com.basewin.services.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.broadcast.BroadcastManage;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.LogUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/18 11:25<br>
 * 描述：流程控制中的基类
 */
public abstract class BaseFlowAty extends BaseActivity implements OnPBOCListener {
    public static final String TAG = BaseFlowAty.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIsLoadPBOC()) {
            try {
                loadPBOC();
            } catch (Exception e) {
                finish();
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果想让pboc监听 控制这个界面的话，就是默认的true， 不想让pboc监听控制 重载这个函数返回false
     *
     * @return
     */
    protected boolean getIsLoadPBOC() {
        return true;
    }

    @Override
    protected void onResume() {
        TDevice.power_shut();
        TDevice.openScreen(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 启动下一个流程
     */
    public final void startNextFlow() {
        try {
            TDevice.power_open();
            TDevice.closeScreen(this);
        } catch (Exception e) {
            Log.e(TAG, "TDevice.power_open();或者TDevice.closeScreen(this);执行出错！");
            e.printStackTrace();
        }
        Log.i(TAG, "启动下一个流程  startNextFlow()...");
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        if (list != null) {
            if (list.size() > 0) {
                //启动下一个流程
                try {
                    String nextAtyStr = list.remove(0);
                    Class nextAty = Class.forName(nextAtyStr);
                    LogUtil.d("[Next Aty]:" + nextAtyStr);
                    Intent intent = new Intent(this, nextAty);
                    intent.putExtras(getIntent());
                    startActivity(intent);
                    finish();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                //流程结束
                Log.d(TAG, "this is the last flow");
                BroadcastManage.sendNextFlowAction(this, FlowControl.MapHelper.getAction());
                FlowControl.MapHelper.clear();
                finish();
            }
        }
    }

    public final String getNextFlowName() {
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        String name = null;
        if (null != list && list.size() > 0) {
            name = list.get(0);
        }
        return name;
    }

    /**
     * 将分支添加到下一个流程之前
     *
     * @param index
     */
    public final void addBranchBeforeNextFlow(int index) {
        ArrayList<ArrayList<String>> arrayLists = (ArrayList<ArrayList<String>>) getIntent()
                .getSerializableExtra(FlowControl.KEY_BRANCH);
        if (index > arrayLists.size()) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index + ", size is " + arrayLists.size());
        }
        List<String> branch = arrayLists.get(index);
        if (branch == null) {
            LogUtil.d("The branch doesn't exist.");
            return;
        }
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        list.addAll(0, branch);
    }

    /**
     * 将后续操作替换为某分支
     *
     * @param index
     */
    public final void replace2Branch(int index) {
        ArrayList<ArrayList<String>> arrayLists = (ArrayList<ArrayList<String>>) getIntent()
                .getSerializableExtra(FlowControl.KEY_BRANCH);
        if (index > arrayLists.size()) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index + ", size is " + arrayLists.size());
        }
        List<String> branch = arrayLists.get(index);
        if (branch == null) {
            LogUtil.d("The branch doesn't exist.");
            return;
        }
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        list.clear();
        list.addAll(branch);
    }

    /**
     * 增加流程节点
     * 增加的流程节点会在此流程之后启动
     *
     * @param next
     */
    public final void addNextFlow(Class... next) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Class c : next) {
            if (!BaseFlowAty.class.isAssignableFrom(c)) {
                throw new IllegalArgumentException("The " + c.getName() + " must extend BaseFlowAty");
            }
            arrayList.add(c.getName());
        }
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        list.addAll(0, arrayList);
    }

    /**
     * 后续是否还有流程
     *
     * @return
     */
    public final boolean hasNextFlow() {
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除下一个节点
     *
     * @return String className 删除节点的名称
     */
    public final String removeNextFlow() {
        List<String> list = getIntent().getStringArrayListExtra(FlowControl.KEY_FLOW_LIST);
        if (list.size() > 0) {
            return list.remove(0);
        }
        return "";
    }

    //************************************pboc监听
    protected void loadPBOC() throws Exception {
        TLog.l("loadPBOC  重新加载！");
        ServiceManager.getInstence().getPboc().refreshListener(this);
    }

    @Override
    public void onError(Intent intent) throws RemoteException {
        TLog.e(TAG, "111onError");
        int code = intent.getIntExtra("code", 65282);
        String errorMsgByCode = PBOCErrorCode.getErrorMsgByCode(code);
        TLog.showToast(errorMsgByCode);
        finish();
    }

    public void icError() {
        TLog.e(TAG, "icError");
        if (this != null && !this.isFinishing()) {
            TLog.showToast("IC卡拒绝交易！");
            finish();
        }
    }

    @Override
    public void onFindingCard(int i, Intent intent) throws RemoteException {

    }

    @Override
    public void onStartPBOC() throws RemoteException {

    }

    @Override
    public void onSelectApplication(List<String> list) throws RemoteException {

    }

    @Override
    public void onConfirmCertInfo(String s, String s1) throws RemoteException {

    }

    @Override
    public void onConfirmCardInfo(Intent intent) throws RemoteException {

    }

    @Override
    public void onRequestInputPIN(boolean b, int i) throws RemoteException {

    }

    @Override
    public void onAARequestOnlineProcess(Intent intent) throws RemoteException {
        TLog.l("onAARequestOnlineProcess flow");
    }

    @Override
    public void onTransactionResult(int i, Intent intent) throws RemoteException {
        if (i == PBOCTransactionResult.QPBOC_ARQC) {
            new OutputQPBOCResult(intent);
        } else if (i == PBOCTransactionResult.APPROVED) {
            Log.d(TAG, "IC卡交易成功");
//            if (CardType.IC_CARD == mCard.type) {
//                mCard.icCardInfo = new OutputCardInfoData(intent);
//                mCard.icAAData = new OutputPBOCAAData(intent);
//            } else if (CardType.RF_CARD == mCard.type) {
//                new OutputQPBOCResult(intent);
//            }
        } else if (i == PBOCTransactionResult.TERMINATED) {
            // IC卡交易拒绝，如果联机成功了需要冲正
            Log.d(TAG, "IC卡交易拒绝,如果联机成功了需要冲正");
            icError();
        }
    }

    @Override
    public void onRequestAmount() throws RemoteException {

    }

    @Override
    public void onReadECBalance(Intent intent) throws RemoteException {

    }

    @Override
    public void onReadCardOfflineRecord(Intent intent) throws RemoteException {

    }
}
