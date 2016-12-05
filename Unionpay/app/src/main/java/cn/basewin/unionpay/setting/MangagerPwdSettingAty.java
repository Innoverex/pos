package cn.basewin.unionpay.setting;

import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.OperatorInfo;
import cn.basewin.unionpay.view.InputWidget;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/9 12:30<br>
 * 描述：操作员密码管理
 */
public class MangagerPwdSettingAty extends BaseSysSettingAty {

    private InputWidget iw_operator_number, iw_operator_old_pw, iw_operator_new_pw, iw_operator_new_pw2;

    private void initViews() {
        iw_operator_number = (InputWidget) findViewById(R.id.iw_operator_number);
        iw_operator_old_pw = (InputWidget) findViewById(R.id.iw_operator_old_pw);
        iw_operator_new_pw = (InputWidget) findViewById(R.id.iw_operator_new_pw);
        iw_operator_new_pw2 = (InputWidget) findViewById(R.id.iw_operator_new_pw2);

        iw_operator_number.setEditType(EditorInfo.TYPE_CLASS_NUMBER);
        iw_operator_old_pw.setEditType(EditorInfo.TYPE_CLASS_NUMBER);
        iw_operator_new_pw.setEditType(EditorInfo.TYPE_CLASS_NUMBER);
        iw_operator_new_pw2.setEditType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_managepwdset;
    }

    @Override
    public String getAtyTitle() {
        return "操作员密码管理";
    }

    @Override
    public void afterSetContentView() {
        initViews();
    }

    @Override
    public void save() {
        String account = iw_operator_number.getEditTextText();
        String oldPwd = iw_operator_old_pw.getEditTextText();
        String newPwd1 = iw_operator_new_pw.getEditTextText();
        String newPwd2 = iw_operator_new_pw2.getEditTextText();

        if (!newPwd1.equals(newPwd2)) {
            Toast.makeText(mContext, "请输入相同的密码", Toast.LENGTH_SHORT).show();
            iw_operator_new_pw.setEditText("");
            iw_operator_new_pw2.setEditText("");
            return;
        }

        try {
            int result = OperatorInfo.dbUpdate(account, oldPwd, newPwd1);
            switch (result) {
                case OperatorInfo.success:
                    showModifyHint();
                    break;
                case OperatorInfo.error_name_pw_null:
                    iw_operator_number.setEditText("");
                    iw_operator_old_pw.setEditText("");
                    iw_operator_new_pw.setEditText("");
                    iw_operator_new_pw2.setEditText("");
                    Toast.makeText(mContext, "账户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
