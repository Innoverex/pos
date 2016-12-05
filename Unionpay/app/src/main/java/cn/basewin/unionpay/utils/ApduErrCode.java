package cn.basewin.unionpay.utils;

public class ApduErrCode {
    public static final int SUCCESS = 0;
    public static final int ERR_BASE = -10000;
    public static final int ERR_NOCARD = ERR_BASE - 1;
    public static final int ERR_RESETERR = ERR_BASE - 2;
    public static final int ERR_CMDERR = ERR_BASE - 3;
    public static final int ERR_COMERR = ERR_BASE - 4;
    public static final int ERR_SECURITYERR = ERR_BASE - 5;
    public static final int ERR_PARAMERR = ERR_BASE - 6;

    public static final int ERR_PASSWORD = ERR_BASE - 7;//密码校验
    public static final int ERR_CHOOSE_FILE = ERR_BASE - 8;//选择文件
    public static final int ERR_READ_COUNT = ERR_BASE - 9;//读取密钥条数
    public static final int ERR_READ_MY = ERR_BASE - 10;//读取密钥
    public static final int ERR_DATE_TIME = ERR_BASE - 11;//有效期
    public static final int ERR_JIEMI = ERR_BASE - 12;//解密
    public static final int ERR_JIAOYAN = ERR_BASE - 13;//校验
    public static final int ERR_SUOYING_BUCUNZAI = ERR_BASE - 14;//索引号不存在
    public static final int ERR_LOAD_TMK = ERR_BASE - 15;//密钥写入失败
    public static final int ERR_APDU = ERR_BASE - 16;//APDU交互失败

    public static String ProcErr(int errno) {
        switch (errno) {
            case SUCCESS:
                return "成功";
            case ERR_NOCARD:
                return "请插入卡片";
            case ERR_RESETERR:
                return "复位失败";
            case ERR_CMDERR:
                return "命令错误";
            case ERR_SECURITYERR:
                return "安全条件不满足";
            case ERR_PARAMERR:
                return "参数错误";
            case ERR_COMERR:
                return "通讯失败";

            case ERR_PASSWORD:
                return "密码校验失败";
            case ERR_CHOOSE_FILE:
                return "选择文件失败";
            case ERR_READ_COUNT:
                return "读取密钥条数失败";
            case ERR_READ_MY:
                return "读取密钥失败";
            case ERR_DATE_TIME:
                return "有效期验证失败";
            case ERR_JIEMI:
                return "TMK解密失败";
            case ERR_JIAOYAN:
                return "TMK校验失败";
            case ERR_SUOYING_BUCUNZAI:
                return "查找密钥失败";
            case ERR_LOAD_TMK:
                return "密钥写入失败";
            case ERR_APDU:
                return "APDU交互失败";

            default:
                return "未知错误";
        }
    }
}