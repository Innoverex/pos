package cn.basewin.unionpay;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/23 16:20<br>
 * 描述: 全局属性<br>
 */
public class AppConfig {
    /**
     * 软件分版本号
     */
    public static final String VERSION_NUM = "310003";
    //交易
    public static final String TRACE_SUCCESS = "1";
    //默认金额
    public static final String money_default = "0.00";
    //账户
    public static final String operator_sys = "99";//系统管理员账户
    public static final String operator_staff = "00";//操作员管理账户
    public static final String operator_sys_default_pw = "12345678";//默认系统帐号密码
    public static final String operator_staff_default_pw = "123456";//默认操作员管理账户密码
    public static final String DEFAULT_VALUE_SAVE_PWD = "888888";//安全密码
    public static final int operator_sys_length = 8;//系统管理密码长度
    public static final int operator_staff_length = 6;//操作员管理密码长度
    public static final int operator_default_length = 4;//普通默认长度
    public static final int operator_name_default_length = 2;//普通默认账户的长度

    public static final String pw_supervisor = "123456";//主管操作员密码

    public static final boolean LOAD_TMK_USE_EXTERNAL_APP = false;//是否使用外部的app导入密钥，否则使用集成的
    //pos 机型
    public static final String POS_MODEL = Build.MODEL;
    public static final String POS_P2000 = "P2000";
    public static final String POS_P8000 = "P8000";
    private static final String TAG = AppConfig.class.getSimpleName();

    /**
     * 是否带物理键盘 的机型
     *
     * @return true 是物理键盘  false 没有物理键盘
     */
    public static boolean hasPhysicalKey() {
        Log.i(TAG, POS_MODEL);
        if (POS_MODEL.substring(0, 5).equals(POS_P8000)) {
            return true;
        } else {
            return false;
        }
    }

    //--------------------------intent--key---------------------------
    public static final String KEY_REQUEST_CODE = "requestCode";
    //交易相关金额
    public static final String KEY_MONEY = "money";
    public static final byte[] POS_PW_LENGTH = new byte[]{0, 6};

    //界面返回码
    public static final int RESULT_CODE_INPUT_PRODUCT_ID = 1;//商品编号
    public static final int RESULT_CODE_INPUT_CERTIFICATE = 2;//凭证号
    public static final int RESULT_CODE_CHECK_INFO = 3;//核对交易信息
    public static final int RESULT_CODE_INPUT_MONEY = 4;//输入金额
    public static final int RESULT_CODE_INPUT_AUTHORIZATION = 5;//输入授权码
    public static final int RESULT_CODE_INPUT_DATA = 6;//输入日期
    public static final int RESULT_CODE_INPUT_MANAGE = 7;//核对管理员的账户
    public static final int RESULT_CODE_INPUT_NSTALLMENT = 8;//分期期数
    public static final int RESULT_CODE_INPUT_TERMINAL = 9;//终端号
    public static final int RESULT_CODE_INPUT_BATCH = 10;//批次号
    public static final int RESULT_CODE_SIGNATURE = 11;//电子签名
    public static final int RESULT_CODE_INPUT_REFER_NO = 12;//参考号
    public static final int RESULT_CODE_INPUT_CARD_NUMBER = 13;//卡号
    public static final int RESULT_CODE_INPUT_CARD_VALIDITY_PERIOD = 14;//卡有效期
    public static final int RESULT_CODE_INPUT_GOODS_CODE = 15;//商品代码
    public static final int RESULT_CODE_INPUT_CVN = 16;//CVN2
    public static final int RESULT_CODE_INPUT_NAME = 17;//姓名
    public static final int RESULT_CODE_INPUT_PHONE = 18;//手机号
    public static final int RESULT_CODE_INPUT_ID6 = 19;//身份者后6位
    public static final int RESULT_CODE_INPUT_FEE = 20;//小费


    //---------------------------打印默认设置数据-----------------
    public static final String PRINT_FONT = "";
    public static final int PRINT_LINESPACE = 1;
    public static final int PRINT_GRAY = 0;
    public static final int PRINT_BOTTOMFEEDLINE = 5;

    public final static int MAX_MONEY = 100 * 10000 * 10; // 最大金额
    public final static int MAXAMTNUM = 12;  // 最大金额屏幕显示位数

    public final static int PRINT_TIMEOUT = 10;//打印下一联或打印结束不操作自动执行的超时时间
    public static final int DISMISS_DIALOG_TIMEOUT = 40;//单位秒，延时关闭dialog，防止打印时回调出错，dialog不能关闭

    //----------------------------数据库--------------------------
    public static final String POS_NAME = "unionpay"; //这个pos 的数据保存的一级文件路径 每个pos app 必须自己更换这个名字
    public static final String DB_NAME = "posDB.db";//数据库名称
    public static final int Db_Version = 3;//数据库名称
    public static final int number = 10;//db 分页

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + POS_NAME
            + File.separator + "pos_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + POS_NAME
            + File.separator + "download" + File.separator;

    // 默认存放数据库的路径
    public final static String DEFAULT_SAVE_DB_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + POS_NAME
            + File.separator + "db" + File.separator;


    /**
     * 系统默认参数设置
     */

    //联盟积分消费
    public static final boolean DEFAULT_VALUE_CUPBONUS = true;
    //发卡行积分消费
    public static final boolean DEFAULT_VALUE_BONUS = true;
    //联盟积分消费撤销
    public static final boolean DEFAULT_VALUE_CUPBONUS_VOID = true;
    //发卡行积分消费撤销
    public static final boolean DEFAULT_VALUE_BONUS_VOID = true;
    //联盟积分查询
    public static final boolean DEFAULT_VALUE_CUPBONUS_QUERY = true;
    //联盟积分退货
    public static final boolean DEFAULT_VALUE_CUPBONUS_REFUND = true;


    //TPDU
    public static final String DEFAULT_VALUE_TPDU = "6000030000";
    //连接超时时间
    public static final String DEFAULT_VALUE_CONNECT_TIME_OUT = "60";
    //是否支持长链接
    public static final boolean DEFAULT_VALUE_IF_SUPPORT_SOCKET = true;
    //是否支持SSL
    public static final boolean DEFAULT_VALUE_IF_SUPPORT_SSL = true;

    //设置主机IP
    public static final String DEFAULT_VALUE_SET_HOST_IP = "";
    //设置主机端口
    public static final String DEFAULT_VALUE_SET_HOST_PORT = "";
    //设置备用IP
    public static final String DEFAULT_VALUE_SET_BACKUP_IP = "";
    //设置备用端口
    public static final String DEFAULT_VALUE_SET_BACKUP_PORT = "";

    //通讯方式
    public static final String DEFAULT_VALUE_COMMU_TYPE = "WiFi";

    //APN设置
    public static final String DEFAULT_VALUE_APN_SETTING = "";
    //用户名
    public static final String DEFAULT_VALUE_USER_NAME_SETTING = "";
    //用户密码
    public static final String DEFAULT_VALUE_USER_PWD_SETTING = "";
    //是否启用自定义VPN
    public static final boolean DEFAULT_VALUE_IF_USE_CUSTOM_APN = false;
    //设置主机IP
    public static final String DEFAULT_VALUE_SET_WIRELESS_HOST_IP = "";
    //设置主机端口
    public static final String DEFAULT_VALUE_SET_WIRELESS_HOST_PORT = "";
    //设置备用IP
    public static final String DEFAULT_VALUE_SET_WIRELESS_BACKUP_IP = "";
    //设置备用端口
    public static final String DEFAULT_VALUE_SET_WIRELESS_BACKUP_PORT = "";

    //外线号码
    public static final String DEFAULT_VALUE_OUTSIDE_CALL = "";
    //主机电话1
    public static final String DEFAULT_VALUE_MAIN_PHONE_NUM_1 = "";
    //主机电话2
    public static final String DEFAULT_VALUE_MAIN_PHONE_NUM_2 = "";
    //主机电话3
    public static final String DEFAULT_VALUE_MAIN_PHONE_NUM_3 = "";
    //AT指令
    public static final String DEFAULT_VALUE_AT_ORDER = "";
    //拨号方式
    public static final String DEFAULT_VALUE_DIAL_STYLE = "音频";
    //Patch
    public static final String DEFAULT_VALUE_PATCH = "";

    //是否使用DHCP
    public static final boolean DEFAULT_VALUE_IS_USE_DHCP = true;
    //设置本机IP
    public static final String DEFAULT_VALUE_SET_CP_IP = "";
    //设置网关IP
    public static final String DEFAULT_VALUE_SET_GATEWAY_IP = "";
    //设置子网掩码
    public static final String DEFAULT_VALUE_SET_SUBNET_MASK = "";
    //设置主机IP
    public static final String DEFAULT_VALUE_SET_DHCP_HOST_IP = "";
    //设置主机端口
    public static final String DEFAULT_VALUE_SET_DHCP_HOST_PORT = "";
    //设置备用IP
    public static final String DEFAULT_VALUE_SET_DHCP_BACKUP_IP = "";
    //设置备用端口
    public static final String DEFAULT_VALUE_SET_DHCP_BACKUP_PORT = "";

    //接触式电子现金消费
    public static final boolean DEFAULT_VALUE_EC_SALE = true;
    //非接快速支付消费
    public static final boolean DEFAULT_VALUE_EC_QUICKPASS = true;
    //电子现金指定账户圈存
    public static final boolean DEFAULT_VALUE_ECLOAD_ACCOUNT = true;
    //电子现金非指定账户圈存
    public static final boolean DEFAULT_VALUE_ECLOAD_NONACCOUNT = true;
    //电子现金现金充值
    public static final boolean DEFAULT_VALUE_ECLOAD_CASH = true;
    //电子现金撤销
    public static final boolean DEFAULT_VALUE_ECLOAD_CASH_VOID = true;
    //电子现金脱机退货
    public static final boolean DEFAULT_VALUE_EC_REFUND = true;

    //分期付款消费
    public static final boolean DEFAULT_VALUE_INSTALLMENT = true;
    //分期付款消费撤销
    public static final boolean DEFAULT_VALUE_INSTALLMENT_VOID = true;

    //商户号
    public static final String DEFAULT_VALUE_MERCHANT_NO = "";
    //终端号
    public static final String DEFAULT_VALUE_TERMINAL_NO = "";
    //商户名
    public static final String DEFAULT_VALUE_MERCHANT_NAME = "";
    //英文名
    public static final String DEFAULT_VALUE_MERCHANT_EN_NAME = "";
    //子应用名
    public static final String DEFAULT_VALUE_SUB_APP_NAME = "";

    //订购消费
    public static final boolean DEFAULT_VALUE_MOTO_SALE = true;
    //订购消费撤销
    public static final boolean DEFAULT_VALUE_MOTO_VOID = true;
    //订购退货
    public static final boolean DEFAULT_VALUE_MOTO_REFUND = true;
    //订购预授权
    public static final boolean DEFAULT_VALUE_MOTO_AUTH = true;
    //订购预授权撤销
    public static final boolean DEFAULT_VALUE_MOTO_CANCEL = true;
    //订购预授权完成请求
    public static final boolean DEFAULT_VALUE_MOTO_AUTH_COMPLETE = true;
    //订购预授权完成通知
    public static final boolean DEFAULT_VALUE_MOTO_AUTH_SETTLEMENT = true;
    //订购预授权完成撤销
    public static final boolean DEFAULT_VALUE_MOTO_COMPLETE_VOID = true;
    //订购持卡人身份验证
    public static final boolean DEFAULT_VALUE_MOTO_VERIFY = true;

    //磁条卡现金充值
    public static final boolean DEFAULT_VALUE_ACCOUNT_LOAD_CASH = true;
    //磁条卡账户充值
    public static final boolean DEFAULT_VALUE_ACCOUNT_LOAD_ACCOUNT = true;

    //服务热线
    public static final String DEFAULT_VALUE_SERVICE_HOTLINE = "95516";
    //签购单字体选择
    public static final String DEFAULT_VALUE_FONT_TYPE = "中字体";
    //未知发卡行打印名
    public static final String DEFAULT_VALUE_UNKONW_ISSUE_PRINT_NAME = "中国银联";
    //交易明细打印模式
    public static final String DEFAULT_VALUE_TRANSACTION_DETAIL_STYLE = "30行";

    //预约消费
    public static final boolean DEFAULT_VALUE_RESERVATION_SALE = true;
    //预约消费撤销
    public static final boolean DEFAULT_VALUE_RESERVATION_VOID = true;

    //是否支持电子签名
    public static final boolean DEFAULT_VALUE_IF_SUPPORT_ESIGN = true;
    //等待签字时间
    public static final String DEFAULT_VALUE_WAIT_TIME_FOR_SIGN = "";
    //上送签字重试次数
    public static final String DEFAULT_VALUE_UPLOAD_SIGN_TRY_TIME = "";
    //两笔联机交易间最大离线交易笔数
    public static final String DEFAULT_VALUE_OFF_LINE_BETWEEN_ONLINE = "";
    //电子签名最大交易笔数
    public static final String DEFAULT_VALUE_MAX_COUNT_OF_SIGN = "";
    //电子签名重签次数
    public static final String DEFAULT_VALUE_ESIGN_TRY_TIME = "";
    //是否输入手机号码
    public static final boolean DEFAULT_VALUE_IF_INPUT_PHONE_NUM = false;
    //是否支持分包上送
    public static final boolean DEFAULT_VALUE_IF_SUPPORT_SUBPACKAGE_UPLOAD = false;

    //流水号
    public static final String DEFAULT_VALUE_TRACE = "000001";
    //批次号
    public static final String DEFAULT_VALUE_BATCH = "000001";
    //是否打印中文收单行
    public static final boolean DEFAULT_VALUE_NEED_PRINT_ACQUIRER_NAME = true;
    //是否打印中文发卡行
    public static final boolean DEFAULT_VALUE_NEED_PRINT_ISSUER_NAME = true;
    //套打签购单样式
    public static final String DEFAULT_VALUE_SALES_SLIP_TYPE = "";
    //热敏打印联数
    public static final String DEFAULT_VALUE_PRINT_NUMBER = "2";
    //签购单是否打印英文
    public static final boolean DEFAULT_VALUE_SLIP_HAS_ENGLISH = true;
    //冲正重发次数
    public static final String DEFAULT_VALUE_REVERSE_TIMES = "3";
    //最大交易笔数
    public static final String DEFAULT_VALUE_MAX_TRADE_NUMBER = "";
    //内外置密码键盘
    public static final String DEFAULT_VALUE_INNER_PINPAD = "";
    //小费比例
    public static final String DEFAULT_VALUE_FEE_RATE = "70";
    //撤销/退货类交易金额是否打印负号
    public static final boolean DEFAULT_VALUE_PRINT_VOID_MINUS = false;
    //签到重发次数
    public static final String DEFAULT_VALUE_SIGN_TIMES = "3";
    //拨号重发次数
    public static final String DEFAULT_VALUE_DIAL_TIMES = "";

    //消费撤销是否输密
    public static final boolean DEFAULT_VALUE_NEED_PIN_VOID = true;
    //分期付款撤销是否输密
    public static final boolean DEFAULT_VALUE_NEED_PIN_INSTALLMENT_VOID = true;
    //预授权撤销是否输密
    public static final boolean DEFAULT_VALUE_NEED_PIN_CANCEL = true;
    //预授权完成撤销是否输密
    public static final boolean DEFAULT_VALUE_NEED_PIN_COMPLETE_VOID = true;
    //预授权完成（请求）是输密
    public static final boolean DEFAULT_VALUE_NEED_PIN_AUTH_COMPLETE = true;

    //离线上送方式
    public static final String DEFAULT_VALUE_UPLOAD_TYPE = "联机前";
    //离线上送重发次数（1-9）
    public static final String DEFAULT_VALUE_UPLOAD_TIMES = "3";
    //自动上送累计笔数
    public static final String DEFAULT_VALUE_UPLOAD_TOTAL_NUMBERS = "10";

    //是否输入主管密码
    public static final boolean DEFAULT_VALUE_NEED_MANAGER_PWD = true;
    //是否允许手输卡号
    public static final boolean DEFAULT_VALUE_NEED_GETCARD_MANUALLY = true;
    //默认刷卡交易
    public static final String DEFAULT_VALUE_DEFAULT_TRANS = "消费";
    //退货限额
    public static final String DEFAULT_VALUE_REFUND_LIMIT = "10000.00";
    //磁道是否加密
    public static final boolean DEFAULT_VALUE_NEED_TRACK_ENCRYPT = false;
    //预授权是否允许手输卡号
    public static final boolean DEFAULT_VALUE_NEED_GETCARD_MANUALLY_AUTH = false;

    //结算是否自动签退
    public static final boolean DEFAULT_VALUE_NEED_AUTO_SIGNOUT = true;
    //结算是否打印明细
    public static final boolean DEFAULT_VALUE_NEED_PRINT_DETAIL = true;
    //是否提示打印失败明细
    public static final boolean DEFAULT_VALUE_NEED_PRINT_FAILURE = true;

    //消费撤销是否刷卡
    public static final boolean DEFAULT_VALUE_NEED_CARD_VOID = true;
    //预授权完成撤销是否刷卡
    public static final boolean DEFAULT_VALUE_NEED_CARD_COMPLETE_VOID = true;

    //消费
    public static final boolean DEFAULT_VALUE_SALE = true;
    //消费撤销
    public static final boolean DEFAULT_VALUE_VOID = true;
    //退货
    public static final boolean DEFAULT_VALUE_REFUND = true;
    //余额查询
    public static final boolean DEFAULT_VALUE_QUERY_BALANCE = true;
    //预授权
    public static final boolean DEFAULT_VALUE_AUTH = true;
    //预授权撤销
    public static final boolean DEFAULT_VALUE_CANCEL = true;
    //预授权完成（请求）
    public static final boolean DEFAULT_VALUE_AUTH_COMPLETE = true;
    //预授权完成（通知）
    public static final boolean DEFAULT_VALUE_AUTH_SETTLEMENT = true;
    //预授权完成撤销
    public static final boolean DEFAULT_VALUE_COMPLETE_VOID = true;
    //离线结算
    public static final boolean DEFAULT_VALUE_OFFLINE_SETTLEMENT = true;
    //结算调整
    public static final boolean DEFAULT_VALUE_OFFLINE_ADJUST = true;
    //小费
    public static final boolean DEFAULT_VALUE_FEE = true;
    //小额代授权
    public static final boolean DEFAULT_VALUE_SMALL_AUTH = true;

    //手机消费
    public static final boolean DEFAULT_VALUE_MOBILE_CONSUME = false;
    //手机消费撤销
    public static final boolean DEFAULT_VALUE_MOBILE_REVOKE_CONSUME = false;
    //手机芯片退货
    public static final boolean DEFAULT_VALUE_MOBILE_RETURN_GOODS = false;
    //手机芯片预授权
    public static final boolean DEFAULT_VALUE_MOBILE_AUTHORIZATION = false;
    //手机芯片预授权撤销
    public static final boolean DEFAULT_VALUE_MOBILE_REVOKE_PRE_AUTHORIZATION = false;
    //手机芯片预授权完成（请求）
    public static final boolean DEFAULT_VALUE_MOBILE_ASK_PREAUTHORIZATION = false;
    //手机芯片预授权完成（通知）
    public static final boolean DEFAULT_VALUE_MOBILE_NOTIFY_PREAUTHORIZATION = false;
    //手机芯片预授权完成撤销
    public static final boolean DEFAULT_VALUE_MOBILE_REVOKE_AFTER_PREAUTHORIZATION = false;
    //手机芯片余额查询
    public static final boolean DEFAULT_VALUE_MOBILE_QUERY = false;
}