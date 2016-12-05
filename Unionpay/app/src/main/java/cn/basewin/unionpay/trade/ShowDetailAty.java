package cn.basewin.unionpay.trade;

import android.os.Bundle;

import org.xutils.ex.DbException;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;

public class ShowDetailAty extends BaseShowDetailAty {
    private static final String TAG = ShowDetailAty.class.getName();
    private List<TransactionData> trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            trans = TransactionDataDao.selectAllOrderValid("id");

            //debug
            TransactionDataDao.showDb(trans);
            TransactionDataDao.showDbAll();

            if (null == trans || trans.size() < 1) {
                TLog.showToast("当前无交易");
                finish();
                return;
            }
            for (int i = 0; i < trans.size(); i++) {
                TransactionData td = trans.get(i);
                String[] l = UIDataHelper.detail();
                String[] r = new String[5];
                setName(td.getName());
                r[0] = TDevice.hiddenCardNum(td.getPan());
                r[1] = td.getTrace();
                r[2] = td.getAuthCode();
                r[3] = PosUtil.changeAmout(td.getAmount());
                r[4] = TDevice.formatDateYDT(td.getYear() + td.getDate() + td.getTime());
                setData(l, r);
            }
            excute();
        } catch (DbException e) {
            finish();
            e.printStackTrace();
        }
        List<List<Map>> map = getMap();
        for (List<Map> m : map) {
            TLog.l(m.toString());
        }
    }

}
