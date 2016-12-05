package cn.basewin.unionpay.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.ex.DbException;

import java.util.List;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.OperatorInfo;
import cn.basewin.unionpay.ui.adapter.StaffAdapter;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.AddAccountDialog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/9 14:10<br>
 * 描述：查柜员
 */
public class ManagerQueryAty extends BaseSysSettingAty {
    /**
     * 柜员
     */
    private ListView lv_operator_list;
    private TextView tv_operator_hint;
    private StaffAdapter staffAdapter;
    private AddAccountDialog mDialog;

    private void initViews() {
        lv_operator_list = (ListView) findViewById(R.id.lv_operator_list);
        tv_operator_hint = (TextView) findViewById(R.id.tv_operator_hint);
        staffAdapter = new StaffAdapter(this, null, R.layout.item_staff) {
            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                if (this.getDatas() == null || this.getDatas().size() <= 0) {
                    showDataIsNull();
                } else {
                    unshowDataIsNull();
                }
            }
        };
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_managershow;
    }

    @Override
    public String getAtyTitle() {
        return "查柜员";
    }

    @Override
    public void afterSetContentView() {
        initViews();
        setRightBtnText("增加");
        initQueryAccount();
    }

    @Override
    public void save() {
        addUser();
    }

    //初始化查柜员view
    private void initQueryAccount() {
        List<OperatorInfo> operatorInfoBeen = null;
        try {
            operatorInfoBeen = OperatorInfo.dbGetAll();
        } catch (DbException e) {
            TLog.showToast("数据取出异常，请重试！");
            e.printStackTrace();
        }
        if (operatorInfoBeen == null || operatorInfoBeen.size() <= 0) {
            showDataIsNull();
            return;
        }
        staffAdapter.setDatas(operatorInfoBeen);
        lv_operator_list.setAdapter(staffAdapter);
    }

    //增加柜员
    public void addUser() {
        mDialog = new AddAccountDialog(this);
        mDialog.setAddAccountDialogClickListening(new AddAccountDialog.AddAccountDialogClickListening() {
            @Override
            public void ok(String account, String pw, String newpw) {
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pw) || TextUtils.isEmpty(newpw)) {
                    TLog.showToast(getString(R.string.name_pw_is_not_null));
                    return;
                }
                if (!pw.equals(newpw)) {
                    TLog.showToast(getString(R.string.Two_password_input_is_not_the_same));
                    return;
                }
                if (account.equals(AppConfig.operator_sys) || account.equals(AppConfig.operator_staff)) {
                    TLog.showToast(getString(R.string.Not_allowed_to_add));
                    return;
                }

                try {
                    int i = OperatorInfo.dbSave(account, pw);
                    switch (i) {
                        case OperatorInfo.success:
                            initQueryAccount();
                            mDialog.dismiss();
                            break;
                        case OperatorInfo.error_name_exist:
                            TLog.showToast(getString(R.string.The_operator_to_exist));
                            break;
                        default:
                            TLog.showToast(getString(R.string.Save_failed));
                            break;
                    }
                } catch (DbException e) {
                    TLog.showToast(getString(R.string.Save_failed));
                    e.printStackTrace();
                }
            }

            @Override
            public void cancel() {

            }
        });
        mDialog.show();
    }

    //保存增加
    private void addOperator() {
        TLog.showToast(getString(R.string.operator_is_changed));
    }

    //查柜员没数据时的提示
    private void showDataIsNull() {
        tv_operator_hint.setVisibility(View.VISIBLE);
        tv_operator_hint.setText("目前没有账户信息！");
    }

    private void unshowDataIsNull() {
        tv_operator_hint.setVisibility(View.GONE);
        tv_operator_hint.setText("");
    }
}
