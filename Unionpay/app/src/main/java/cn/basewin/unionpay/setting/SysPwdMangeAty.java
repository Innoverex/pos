package cn.basewin.unionpay.setting;

import android.widget.EditText;
import android.widget.Toast;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 16:57<br>
 * 描述：系统管理密码及安全密码
 */
public class SysPwdMangeAty extends BaseSysSettingAty {
    private static final String TAG = SysPwdMangeAty.class.getName();
    /**
     * 系统管理密码
     */
    public static final String KEY_SYS_PWD = TAG + "sys_pwd";
    /**
     * 安全密码
     */
    public static final String KEY_SAVE_PWD = TAG + "save_pwd";
    /**
     * 密码管理方式
     */
    public static final String EXTRA_TYPE = "pwd_manage_type";
    private String type;
    /**
     * 请输入原密码
     */
    private EditText et_change_password_input_old;
    /**
     * 请输入新密码
     */
    private EditText et_change_password_input_new;
    /**
     * 请再次输入新密码
     */
    private EditText et_input_new_pwd_again;

    @Override
    protected void beforeSetContentView() {
        type = getIntent().getExtras().getString(EXTRA_TYPE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_pwdmanage;
    }

    @Override
    public String getAtyTitle() {
        if (type.equals(KEY_SAVE_PWD)) {
            return getString(R.string.change_password_secure);
        }
        return getString(R.string.input_sys_manage_pwd);
    }

    @Override
    public void afterSetContentView() {
        et_change_password_input_old = (EditText) findViewById(R.id.et_change_password_input_old);
        et_change_password_input_new = (EditText) findViewById(R.id.et_change_password_input_new);
        et_input_new_pwd_again = (EditText) findViewById(R.id.et_input_new_pwd_again);
    }

    @Override
    public void save() {
        String oldPwdTxt = et_change_password_input_old.getText().toString();
        String newPwdTxt = et_change_password_input_new.getText().toString();
        String newPwdAgainTxt = et_input_new_pwd_again.getText().toString();
        if (type.equals(KEY_SAVE_PWD)) {
            //修改安全密码
            String oldPwd = SPTools.get(KEY_SAVE_PWD, AppConfig.DEFAULT_VALUE_SAVE_PWD);
            if (checkPwd(oldPwd, oldPwdTxt)) {
                if (checkPwd(newPwdTxt, newPwdAgainTxt)) {
                    SPTools.set(KEY_SAVE_PWD, newPwdTxt);
                    clear();
                    Toast.makeText(mContext, "修改安全密码成功", Toast.LENGTH_SHORT).show();
                } else {
                    TLog.showToast("请输入相同的密码");
                }
            } else {
                TLog.showToast("密码错误,修改失败");
                clear();
            }

        } else {
            //修改系统管理密码
            String oldPwd = SPTools.get(KEY_SYS_PWD, "");
            if (checkPwd(oldPwd, oldPwdTxt)) {
                if (checkPwd(newPwdTxt, newPwdAgainTxt)) {
                    SPTools.set(KEY_SYS_PWD, newPwdTxt);
                    clear();
                    Toast.makeText(mContext, "修改系统管理密码成功", Toast.LENGTH_SHORT).show();
                } else {
                    TLog.showToast("请输入相同的密码");
                }
            } else {
                TLog.showToast("密码错误,修改失败");
                clear();
            }
            SPTools.set(KEY_SYS_PWD, et_change_password_input_new.getText().toString());
        }
    }

    /**
     * 校验密码是否相同
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    private boolean checkPwd(String oldPwd, String newPwd) {
        if (oldPwd.equals(newPwd)) {
            return true;
        }
        return false;
    }

    private void clear() {
        et_change_password_input_old.setText("");
        et_change_password_input_new.setText("");
        et_input_new_pwd_again.setText("");
    }
}
