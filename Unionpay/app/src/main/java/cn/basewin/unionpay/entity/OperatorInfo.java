package cn.basewin.unionpay.entity;

import android.text.TextUtils;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.R;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/23 15:28<br>
 * 描述: 操作员帐号密码的实例<br>
 */
@Table(name = "OperatorInfo")
public class OperatorInfo {

    public static final int success = 0;
    public static final int error_name_null = 1;//用户名为null
    public static final int error_pw_null = 2;//密码为null
    public static final int error_name_pw_null = 3;//密码为null ||账户或者密码错误
    public static final int error_name_exist = 4;//用户名以存在
    public static final int error_unknown = 99;//未知错误

    public static int showHint(int i) {
        int code = 0;
        switch (i) {
            case success:
                code = R.string.operator_is_changed;
                break;
            case error_name_null:
                code = R.string.name_pw_is_not_null;
                break;
            case error_pw_null:
                code = R.string.name_pw_is_not_null;
                break;
            case error_name_pw_null:
                code = R.string.name_pw_is_not_null;
                break;
            case error_name_exist:
                code = R.string.The_operator_to_exist;
                break;
            default:
                code = R.string.error_unknown;
                break;
        }
        return code;
    }

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "pw")
    private String pw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "OperatorInfo{" +
                "name='" + name + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }

    public OperatorInfo() {
    }

    private static DbManager db() {
        return AppContext.db();
    }

    //校验帐号密码
    public static boolean dbCheck(String _name, String _pw) throws DbException {
        boolean b = false;
        OperatorInfo o = dbGetByName(_name);
        if (o == null) {
            return b;
        }
        if (o.getPw().equals(_pw)) {
            b = true;
        }
        return b;
    }

    //通过用户名来查询数据库获取实例
    public static OperatorInfo dbGetByName(String _name) throws DbException {
        OperatorInfo _data = null;
        _data = db().selector(OperatorInfo.class).where("name", "=", _name).findFirst();
        return _data;
    }

    //保存数据到数据库
    public static int dbSave(String _name, String _pw) throws DbException {
        OperatorInfo obj = new OperatorInfo();
        obj.setName(_name);
        obj.setPw(_pw);
        return dbSave(obj);
    }

    //保存操作员信息
    public static int dbSave(OperatorInfo _obj) throws DbException {
        if (_obj == null) {
            return error_name_pw_null;
        }
        if (TextUtils.isEmpty(_obj.getName())) {
            return error_name_null;
        }
        if (TextUtils.isEmpty(_obj.getPw())) {
            return error_pw_null;
        }
        if (dbGetByName(_obj.getName()) != null) {
            return error_name_exist;
        }
        db().save(_obj);
        return success;
    }

    //通过账户密码修改密码
    public static int dbUpdate(String _name, String _pw, String newPW) throws DbException {
        if (!dbCheck(_name, _pw)) {
            return error_name_pw_null;
        }
        OperatorInfo obj = dbGetByName(_name);
        if (obj == null) {
            return error_name_pw_null;
        }
        obj.setPw(newPW);
        db().update(obj, "pw");
        return success;
    }

    public static List<OperatorInfo> dbGetAll() throws DbException {
        List<OperatorInfo> all = null;
        all = db().findAll(OperatorInfo.class);
        return all;
    }

    //通过名字删除数据
    public static boolean dbDeleteByName(String msg) {
        try {
            OperatorInfo data = dbGetByName(msg);
            if (data == null) {
                return false;
            }
            dbDelete(data);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean dbDelete(OperatorInfo obj) {
        try {
            db().delete(obj);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    //初始化管理员 用户，柜员管理的帐号是一开始就要存在与数据库中的。
    public static void initOperatorUser() {
        try {
            dbSave(AppConfig.operator_staff, AppConfig.operator_staff_default_pw);
            dbSave("01", "0000");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
