
package cn.basewin.unionpay;

import android.text.TextUtils;

import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputReferNo;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 10:46<br>
 * 描述: POS2.0基础交易类型码定义 <br>
 */
public class ActionConstant {
    /**
     * 消费
     */
    public final static int ACTION_SALE = 1;
    /**
     * 撤销
     */
    public final static int ACTION_VOID = 2;
    /**
     * 退货
     */
    public final static int ACTION_REFUND = 3;

    //预授权类交易
    /**
     * 预授权
     */
    public final static int ACTION_AUTH = 4;
    /**
     * 预授权撤销
     */
    public final static int ACTION_CANCEL = 5;
    /**
     * 预授权完成(通知)
     */
    public final static int ACTION_AUTH_SETTLEMENT = 6;
    /**
     * 预授权完成(请求)
     */
    public final static int ACTION_AUTH_COMPLETE = 7;
    /**
     * 预授权完成撤销
     */
    public final static int ACTION_COMPLETE_VOID = 8;
    /**
     * 查询余额
     */
    public final static int ACTION_QUERY_BALANCE = 15;
    //打印
    /**
     * 重打印最后一笔
     */
    public final static int ACTION_PRINT_LAST = 16;
    /**
     * 重打印任意一笔
     */
    public final static int ACTION_PRINT_RANDOM = 17;
    /**
     * 打印交易明细
     */
    public final static int ACTION_PRINT_DETAIL = 18;
    /**
     * 打印交易汇总
     */
    public final static int ACTION_PRINT_SUMMARY = 19;
    /**
     * 重打印结算单
     */
    public final static int ACTION_PRINT_SETTLEMENT = 20;
    //离线
    /**
     * 离线结算
     */
    public final static int ACTION_OFFLINE = 21;
    /**
     * 离线调整
     */
    public final static int ACTION_ADJUST = 22;

    /**
     * 上送电子签名
     */
    public final static int ACTION_UPLOAD_SIGNA = 23;

    public static int getAction(int action) {
        switch (action) {
            case ActionConstant.ACTION_SALE://消费
                return R.string.action_consumption;
            case ActionConstant.ACTION_VOID://撤销
                return R.string.action_revoke;
            case ActionConstant.ACTION_REFUND://退货
                return R.string.action_refund;
            case ActionConstant.ACTION_AUTH://预授权
                return R.string.action_authorization_trading;
            case ActionConstant.ACTION_CANCEL://预授权撤销
                return R.string.action_authorization_undo;
            case ActionConstant.ACTION_AUTH_SETTLEMENT://预授权完成(通知)
                return R.string.action_authorization_over_notice;
            case ActionConstant.ACTION_AUTH_COMPLETE://预授权完成(请求)
                return R.string.action_authorization_over_request;
            case ActionConstant.ACTION_COMPLETE_VOID://预授权完成撤销
                return R.string.action_authorization_over_undo;
            case ActionConstant.ACTION_QUERY_BALANCE://查询余额
                return R.string.action_query_balance;
            case ActionConstant.ACTION_PRINT_LAST://重打印最后一笔
                return R.string.action_print_next;
            case ActionConstant.ACTION_PRINT_RANDOM://重打印任意一笔
                return R.string.action_print_any;
            case ActionConstant.ACTION_PRINT_DETAIL://打印交易明细
                return R.string.action_print_detail;
            case ActionConstant.ACTION_PRINT_SUMMARY://打印交易汇总
                return R.string.action_print_summary;
            case ActionConstant.ACTION_PRINT_SETTLEMENT://重打印结算单
                return R.string.action_print_settlement;
            case ActionConstant.ACTION_OFFLINE://离线结算
                return R.string.action_offline_settlement;
            case ActionConstant.ACTION_ADJUST://离线调整
                return R.string.action_offline_adjust;
            case ActionConstant.ACTION_UPLOAD_SIGNA://上送电子签名
                return R.string.action_upload_signa;
            //-----管理界面下

            //---------电子现金
            case ActionConstant.ACTION_EC_GROUP://电子现金
                return R.string.action_electronic_cash;
            case ActionConstant.ACTION_EC_QUICKPASS://快速支付
                return R.string.action_electronic_cash_fast_pay;
            case ActionConstant.ACTION_EC_SALE://普通支付
                return R.string.action_electronic_cash_ordinary_pay;
            case ActionConstant.ACTION_EC_LOAD_GROUP://普通支付
                return R.string.action_electronic_cash_quancun;
            case ActionConstant.ACTION_EC_QUERY_BALANCE://余额查询
                return R.string.action_electronic_cash_query_balance;
            case ActionConstant.ACTION_EC_QUERY_DETAIL://明显查询
                return R.string.action_electronic_cash_query_detail;
            case ActionConstant.ACTION_EC_REFUND://脱机退货
                return R.string.action_electronic_cash_offline_return;
            case ActionConstant.ACTION_EC_LOAD_LOG://脱机退货
                return R.string.action_electronic_cash_quancun_log;
            ///电子现金下 圈存
            case ActionConstant.ACTION_ECLOAD_CASH://
                return R.string.action_electronic_cash_quancun_up;
            case ActionConstant.ACTION_ECLOAD_ACCOUNT://
                return R.string.action_electronic_cash_quancun_specify;
            case ActionConstant.ACTION_ECLOAD_NONACCOUNT://
                return R.string.action_electronic_cash_quancun_unspecify;
            case ActionConstant.ACTION_ECLOAD_CASH_VOID://
                return R.string.action_electronic_cash_quancun_up_undo;
            //分期付款
            case ActionConstant.ACTION_INSTALLMENT_GROUP://
                return R.string.action_installment;
            case ActionConstant.ACTION_INSTALLMENT://
                return R.string.action_installment_consumption;
            case ActionConstant.ACTION_INSTALLMENT_VOID://
                return R.string.action_installment_consumption_undo;
            //积分
            case ActionConstant.ACTION_BONUS_GROUP://
                return R.string.action_integral;
            case ActionConstant.ACTION_BONUS_GROUP_BONUS://
                return R.string.action_integral_consumption;
            case ActionConstant.ACTION_BONUS://
                return R.string.action_bonus;
            case ActionConstant.ACTION_CUPBONUS://
                return R.string.action_bonus_union;
            case ActionConstant.ACTION_BONUS_VOID_GROUP://积分消费撤销
                return R.string.action_integral_consumption_revoke;
            case ActionConstant.ACTION_BONUS_VOID://发卡行积分消费撤销
                return R.string.action_bonus_void;
            case ActionConstant.ACTION_CUPBONUS_VOID://联盟积分消费撤销
                return R.string.action_bonus_union_void;
            case ActionConstant.ACTION_CUPBONUS_QUERY://
                return R.string.action_integral_query;
            case ActionConstant.ACTION_CUPBONUS_REFUND://
                return R.string.action_integral_return;
            //手机芯片
            case ActionConstant.ACTION_UPCARD_GROUP://
                return R.string.action_chip;
            case ActionConstant.ACTION_UPCARD://
                return R.string.action_chip_consumption;
            case ActionConstant.ACTION_UPCARD_VOID://
                return R.string.action_chip_consumption_return;
            case ActionConstant.ACTION_UPCARD_REFUND://
                return R.string.action_chip_return;
            case ActionConstant.ACTION_UPCARD_AUTH://
                return R.string.action_chip_authorization;
            case ActionConstant.ACTION_UPCARD_CANCEL://
                return R.string.action_chip_authorization_undo;
            case ActionConstant.ACTION_UPCARD_AUTH_COMPLETE://
                return R.string.action_chip_authorization_over_request;
            case ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT://
                return R.string.action_chip_authorization_over_notice;
            case ActionConstant.ACTION_UPCARD_COMPLETE_VOID://
                return R.string.action_chip_authorization_over_undo;
            case ActionConstant.ACTION_UPCARD_QUERY_BALANCE://
                return R.string.action_chip_query_balance;
            //预约消费
            case ActionConstant.ACTION_RESERVATION_GROUP://
                return R.string.action_appointment;
            case ActionConstant.ACTION_RESERVATION_SALE://
                return R.string.action_appointment_consumption;
            case ActionConstant.ACTION_RESERVATION_VOID://
                return R.string.action_appointment_consumption_undo;
            //订购交易
            case ActionConstant.ACTION_MOTO_GROUP://
                return R.string.action_order;
            case ActionConstant.ACTION_MOTO_SALE://
                return R.string.action_order_consumption;
            case ActionConstant.ACTION_MOTO_VOID://
                return R.string.action_order_consumption_undo;
            case ActionConstant.ACTION_MOTO_REFUND://
                return R.string.action_order_return;
            case ActionConstant.ACTION_MOTO_AUTH://
                return R.string.action_order_authorization;
            case ActionConstant.ACTION_MOTO_CANCEL://
                return R.string.action_order_authorization_undo;
            case ActionConstant.ACTION_MOTO_AUTH_COMPLETE://
                return R.string.action_order_authorization_over_request;
            case ActionConstant.ACTION_MOTO_AUTH_SETTLEMENT://
                return R.string.action_order_authorization_over_notice;
            case ActionConstant.ACTION_MOTO_COMPLETE_VOID://
                return R.string.action_order_authorization_over_undo;
            case ActionConstant.ACTION_MOTO_VERIFY://
                return R.string.action_order_cardholder_verification;
            //磁条卡充值
            case ActionConstant.ACTION_ACCOUNT_LOAD_GROUP://
                return R.string.action_stripe_cards;
            case ACTION_ACCOUNT_LOAD_CONFIRM://
                return R.string.action_stripe_cards;
            case ActionConstant.ACTION_ACCOUNT_LOAD_CASH://
                return R.string.action_stripe_cards_rechargeable_cash;
            case ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT://
                return R.string.action_stripe_cards_rechargeable_account;
            //管理
            case ActionConstant.ACTION_SIGN_GROUP://
                return R.string.action_sign;
            case ActionConstant.ACTION_SIGN_OUT://
                return R.string.action_sign_out;
            case ActionConstant.ACTION_QUERY_TRANSACTION://
                return R.string.action_query_transaction;
            case ActionConstant.ACTION_OPER_MANAGEMENT://
                return R.string.action_teller;
            case ActionConstant.ACTION_EXTERNAL_NUMBER://
                return R.string.action_outside_number;
            case ActionConstant.ACTION_SETTLEMENT://
                return R.string.action_settlement;
            case ActionConstant.ACTION_LOCK_TERMINAL://
                return R.string.action_lock_terminal;
            case ActionConstant.ACTION_VERSION://
                return R.string.action_version;
            //签到
            case ActionConstant.ACTION_SIGN_POS://
                return R.string.action_sign_pos;
            case ActionConstant.ACTION_SIGN_OPER://
                return R.string.action_sign_teller;
            case ActionConstant.ACTION_SIGN_BONUS://
                return R.string.action_sign_integral;
            //设置界面菜单
            case ActionConstant.ACTION_SET_MERCHANT://
                return R.string.action_setting_merchant;
            case ActionConstant.ACTION_SET_TRANSACTION://
                return R.string.action_setting_transaction_management;
            case ActionConstant.ACTION_SET_SYSTEM://
                return R.string.action_setting_system;
            case ActionConstant.ACTION_SET_COMMUNICATION://
                return R.string.action_setting_communication;
            case ActionConstant.ACTION_SET_KEY://
                return R.string.action_setting_key;
            case ActionConstant.ACTION_SET_PASSWORD://
                return R.string.action_setting_password;
            case ActionConstant.ACTION_SET_OTHER_GROUP://
                return R.string.action_setting_other;
            case ActionConstant.ACTION_QUERY_TRANSACTION_DETAIL://
                return R.string.action_query_detail;
            case ActionConstant.ACTION_QUERY_TRANSACTION_TOTAL://
                return R.string.action_query_summary;
            case ActionConstant.ACTION_QUERY_TRANSACTION_TRACE://
                return R.string.action_query_for_serial;
            case ActionConstant.ACTION_QUERY_TRANSACTION_CARDNO://
                return R.string.action_query_for_card;

            case ActionConstant.ACTION_INDUSTRY_TRANSFER://
                return R.string.action_industry_transfer;
            case ActionConstant.ACTION_AGREED_AN_INTER_BANK_TRANSFER://
                return R.string.action_agreed_an_inter_bank_transfer;
            case ActionConstant.ACTION_AN_INTER_BANK_TRANSFER_NOT_AGREED://
                return R.string.action_an_inter_bank_transfer_not_agreed;
            case ActionConstant.ACTION_REAL_TIME_COLLECTING_INTERFACE://
                return R.string.action_real_time_collecting_interface;
            case ActionConstant.ACTION_REAL_TIME_PAYMENT_CONFIRMATION_INTERFACE://
                return R.string.action_real_time_payment_confirmation_interface;
            case ActionConstant.ACTION_WITH_THE_QUERY_LETTER_AGREEMENT://
                return R.string.action_with_the_query_letter_agreement;
            case ActionConstant.ACTION_WITH_THE_LETTER_TO_APPLY_FOR://
                return R.string.action_with_the_letter_to_apply_for;
            case ActionConstant.ACTION_REPAYMENT_BEFOREHAND_QUERY://
                return R.string.action_repayment_beforehand_query;
            case ActionConstant.ACTION_REIMBURSEMENT://
                return R.string.action_reimbursement;
            case ActionConstant.ACTION_LOAN_LIST_QUERY://
                return R.string.action_loan_list_query;
            case ActionConstant.ACTION_EXISTENTIAL://
                return R.string.action_existential;
            case ActionConstant.ACTION_CASH://
                return R.string.action_cash;
            case ActionConstant.ACTION_CASH_CIRCLE_INTERFACE://
                return R.string.action_cash_circle_interface;
            case ActionConstant.ACTION_CIRCLE_OF_CASH_CONFIRMATION_INTERFACE://
                return R.string.action_circle_of_cash_confirmation_interface;
            case ActionConstant.ACTION_CURRENT_TRANSFER_TIME://
                return R.string.action_current_transfer_time;
            case ActionConstant.ACTION_TURN_CHECKING_REGULARLY://
                return R.string.action_turn_checking_regularly;
            case ActionConstant.ACTION_SMALL_CIRCULAR_MACHINE_DETAILED_QUERY://
                return R.string.action_small_circular_machine_detailed_query;
            case ActionConstant.ACTION_THE_PASSBOOK_TO_FILL://
                return R.string.action_the_passbook_to_fill;
            case ActionConstant.ACTION_UNDERSTAND_FOLD_TO_FILL://
                return R.string.action_understand_fold_to_fill;
            case ActionConstant.ACTION_ACCOUNT_VERIFICATION://
                return R.string.action_account_verification;
            case ActionConstant.ACTION_TO_HELP_FARMERS_TRANSFER://
                return R.string.action_to_help_farmers_transfer;
            case ActionConstant.ACTION_BALANCE_INQUIRY_ON_A_REGULAR_BASIS://
                return R.string.action_balance_inquiry_on_a_regular_basis;
            case ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS://
                return R.string.action_and_he_did_that_to_help_farmers;
            case ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS_RUSHED_WITHDRAWAL_IS://
                return R.string.action_and_he_did_that_to_help_farmers_rushed_withdrawal_is;
            case ActionConstant.ACTION_VIOLATIONS_OF_INFORMATION_QUERY://
                return R.string.action_violations_of_information_query;
            case ActionConstant.ACTION_VIOLATIONS_PAY_COST://
                return R.string.action_violations_pay_cost;
            case ActionConstant.ACTION_VIOLATIONS_TO_EXPEND_THE_INFORMATION_QUERY://
                return R.string.action_violations_to_expend_the_information_query;
            case ActionConstant.ACTION_INQUIRY_OF_THE_PAYMENT://
                return R.string.action_inquiry_of_the_payment;
            case ActionConstant.ACTION_A_PAYMENT_SUCCESS://
                return R.string.action_a_payment_success;
            case ActionConstant.ACTION_BALANCE_INFORMATION_NOTICE://
                return R.string.action_balance_information_notice;
            default:
                return R.string.error_action;
        }

    }

    public static String[] getStatusByAction() {
        String[] s = new String[2];
        switch (FlowControl.MapHelper.getAction()) {
            case ACTION_VOID:
            case ACTION_REFUND:
            case ACTION_COMPLETE_VOID:
            case ACTION_INSTALLMENT_VOID:
                s[0] = "-";
                s[1] = "2";
                updateState();
                break;
            case ACTION_SALE:
            case ACTION_AUTH_SETTLEMENT:
            case ACTION_AUTH_COMPLETE:
            case ACTION_INSTALLMENT:
                s[0] = "+";
                s[1] = "1";
                break;
            case ACTION_AUTH:
                //---------电子现金
            case ACTION_EC_GROUP://电子现金
            case ACTION_EC_QUICKPASS://快速支付
            case ACTION_EC_SALE://普通支付
            case ACTION_EC_LOAD_GROUP://普通支付
            case ACTION_EC_QUERY_BALANCE://余额查询
            case ACTION_EC_QUERY_DETAIL://明显查询
            case ACTION_EC_REFUND://脱机退货
            case ACTION_EC_LOAD_LOG://圈存日志
                ///电子现金下 圈存
            case ACTION_ECLOAD_CASH://
            case ACTION_ECLOAD_ACCOUNT://
            case ACTION_ECLOAD_NONACCOUNT://
            case ACTION_ECLOAD_CASH_VOID://
                //分期付款
            case ACTION_INSTALLMENT_GROUP://
                //积分
            case ACTION_BONUS_GROUP://
            case ACTION_BONUS_GROUP_BONUS://
            case ACTION_BONUS://
            case ACTION_CUPBONUS://
            case ACTION_BONUS_VOID_GROUP://积分消费撤销
            case ACTION_BONUS_VOID://发卡行积分消费撤销
            case ACTION_CUPBONUS_VOID://联盟积分消费撤销
            case ACTION_CUPBONUS_QUERY://
            case ACTION_CUPBONUS_REFUND://
                //手机芯片
            case ACTION_UPCARD_GROUP://
            case ACTION_UPCARD://
            case ACTION_UPCARD_VOID://
            case ACTION_UPCARD_REFUND://
            case ACTION_UPCARD_AUTH://
            case ACTION_UPCARD_CANCEL://
            case ACTION_UPCARD_AUTH_COMPLETE://
            case ACTION_UPCARD_AUTH_SETTLEMENT://
            case ACTION_UPCARD_COMPLETE_VOID://
            case ACTION_UPCARD_QUERY_BALANCE://
                //预约消费
            case ACTION_RESERVATION_GROUP://
            case ACTION_RESERVATION_SALE://
            case ACTION_RESERVATION_VOID://
                //订购交易
            case ACTION_MOTO_GROUP://
            case ACTION_MOTO_SALE://
            case ACTION_MOTO_VOID://
            case ACTION_MOTO_REFUND://
            case ACTION_MOTO_AUTH://
            case ACTION_MOTO_CANCEL://
            case ACTION_MOTO_AUTH_COMPLETE://
            case ACTION_MOTO_AUTH_SETTLEMENT://
            case ACTION_MOTO_COMPLETE_VOID://
            case ACTION_MOTO_VERIFY://
                //磁条卡充值
            case ACTION_ACCOUNT_LOAD_GROUP://
            case ACTION_ACCOUNT_LOAD_CASH://
            case ACTION_ACCOUNT_LOAD_ACCOUNT://
                s[0] = "+";
                s[1] = "";//不参与结算
                break;
            case ACTION_CANCEL:
                s[0] = "-";
                s[1] = "";//不参与结算
                updateState();
                break;
            default:
                break;
        }
        return s;
    }

    private static void updateState() {
        KeyValue kv = new KeyValue("status", "-");
        WhereBuilder wb = null;
        if (!TextUtils.isEmpty(FlowControl.MapHelper.getTrace())) {
            wb = WhereBuilder.b("trace", "=", FlowControl.MapHelper.getTrace());
        } else if (!TextUtils.isEmpty(FlowControl.MapHelper.getAuthCode())) {
            wb = WhereBuilder.b("authCode", "=", FlowControl.MapHelper.getAuthCode());
        } else if (!TextUtils.isEmpty((String) FlowControl.MapHelper.getMap().get(InputReferNo.KEY_DATA))) {
            wb = WhereBuilder.b("referenceNo", "=", FlowControl.MapHelper.getMap().get(InputReferNo.KEY_DATA));
        }
        try {
            TransactionDataDao.update(wb, kv);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //----------------------------开启 菜单的action
    public final static int MENU_MAIN = 10000;//主菜单
    public final static int MENU_AUTH = MENU_MAIN + 2000;//预授权菜单
    public final static int MENU_OFFLINE = MENU_MAIN + 3000;//离线
    public final static int MENU_PRINT = MENU_MAIN + 4000;//打印菜单
    public final static int MENU_ADMIN = MENU_MAIN + 5000;//管理菜单
    public final static int MENU_OTHER = MENU_MAIN + 6000;//其他菜单
    public final static int MENU_CHARACTERISTICS_OF_TRANSACTIONAL = 40000;//特色交易菜单
    public final static int MENU_TRANSFER = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3000;//转账
    public final static int MENU_PAID = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3100;//代付
    public final static int MENU_CREDIT = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3200;//信贷
    public final static int MENU_CASH_AROUND_TO_ASK = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3300;//现金圈提
    public final static int MENU_PASSBOOK = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3400;//存折
    public final static int MENU_HELP_FARMERS = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3500;//助农
    public final static int MENU_BILL_AYMENT = MENU_CHARACTERISTICS_OF_TRANSACTIONAL + 3600;//代缴费

    /**
     * 电子现金
     */
    public final static int ACTION_EC_GROUP = MENU_OTHER + 100;//电子现金
    public final static int ACTION_EC_QUICKPASS = ACTION_EC_GROUP + 1;           //快速支付
    public final static int ACTION_EC_SALE = ACTION_EC_GROUP + 2;        //普通支付
    public final static int ACTION_EC_QUERY_BALANCE = ACTION_EC_GROUP + 3;           //余额查询
    public final static int ACTION_EC_QUERY_DETAIL = ACTION_EC_GROUP + 4;            //明显查询
    public final static int ACTION_EC_REFUND = ACTION_EC_GROUP + 5;          //脱机退货
    public final static int ACTION_EC_LOAD_LOG = ACTION_EC_GROUP + 6;          //圈存日志
    /**
     * 电子现金下 圈存
     */
    public final static int ACTION_EC_LOAD_GROUP = ACTION_EC_GROUP + 10;//电子现金下的圈存
    public final static int ACTION_ECLOAD_CASH = ACTION_EC_LOAD_GROUP + 1;             //现金充值
    public final static int ACTION_ECLOAD_ACCOUNT = ACTION_EC_LOAD_GROUP + 2;  //指定账户圈存
    public final static int ACTION_ECLOAD_NONACCOUNT = ACTION_EC_LOAD_GROUP + 3;  //非指定帐号圈存
    public final static int ACTION_ECLOAD_CASH_VOID = ACTION_EC_LOAD_GROUP + 4;      //现金充值撤销

    /**
     * 分期付款
     */
    public final static int ACTION_INSTALLMENT_GROUP = MENU_OTHER + 400;//
    public final static int ACTION_INSTALLMENT = ACTION_INSTALLMENT_GROUP + 1;//分期付款消费
    public final static int ACTION_INSTALLMENT_VOID = ACTION_INSTALLMENT_GROUP + 2;//分期付款消费撤销

    /**
     * 积分
     */
    public final static int ACTION_BONUS_GROUP = MENU_OTHER + 500;//

    public final static int ACTION_BONUS_GROUP_BONUS = ACTION_BONUS_GROUP + 10;           //积分消费
    public final static int ACTION_BONUS = ACTION_BONUS_GROUP + 11;                     //发卡行积分消费
    public final static int ACTION_CUPBONUS = ACTION_BONUS_GROUP + 12;                //联盟积分消费

    public final static int ACTION_BONUS_VOID_GROUP = ACTION_BONUS_GROUP + 20;  //积分消费撤销
    public final static int ACTION_BONUS_VOID = ACTION_BONUS_GROUP + 21;        //发卡行积分消费撤销
    public final static int ACTION_CUPBONUS_VOID = ACTION_BONUS_GROUP + 22;  //联盟积分消费撤销

    public final static int ACTION_CUPBONUS_QUERY = ACTION_BONUS_GROUP + 30;                  //联盟积分查询
    public final static int ACTION_CUPBONUS_REFUND = ACTION_BONUS_GROUP + 40;                //联盟积分退货


    /**
     * 手机芯片
     */
    public final static int ACTION_UPCARD_GROUP = MENU_OTHER + 600;//
    public final static int ACTION_UPCARD = ACTION_UPCARD_GROUP + 1;      //手机芯片消费
    public final static int ACTION_UPCARD_VOID = ACTION_UPCARD_GROUP + 2;  //手机芯片消费撤销
    public final static int ACTION_UPCARD_REFUND = ACTION_UPCARD_GROUP + 3;       //手机芯片退货
    public final static int ACTION_UPCARD_AUTH = ACTION_UPCARD_GROUP + 4;    //手机预授权
    public final static int ACTION_UPCARD_CANCEL = ACTION_UPCARD_GROUP + 5;    //手机预授权撤销
    public final static int ACTION_UPCARD_AUTH_COMPLETE = ACTION_UPCARD_GROUP + 6;    //手机预授权完成请求
    public final static int ACTION_UPCARD_AUTH_SETTLEMENT = ACTION_UPCARD_GROUP + 7;    //手机预授权完成通知
    public final static int ACTION_UPCARD_COMPLETE_VOID = ACTION_UPCARD_GROUP + 8;    //手机预授权完成撤销
    public final static int ACTION_UPCARD_QUERY_BALANCE = ACTION_UPCARD_GROUP + 9;    //手机余额查询
    /**
     * 预约消费
     */
    public final static int ACTION_RESERVATION_GROUP = MENU_OTHER + 700;
    public final static int ACTION_RESERVATION_SALE = ACTION_RESERVATION_GROUP + 1;      //预约消费
    public final static int ACTION_RESERVATION_VOID = ACTION_RESERVATION_GROUP + 2;  //预约消费撤销
    /**
     * 订购交易
     */
    public final static int ACTION_MOTO_GROUP = MENU_OTHER + 800;
    public final static int ACTION_MOTO_SALE = ACTION_MOTO_GROUP + 1;//订购消费
    public final static int ACTION_MOTO_VOID = ACTION_MOTO_GROUP + 2;//订购消费撤销
    public final static int ACTION_MOTO_REFUND = ACTION_MOTO_GROUP + 3;//订购退货
    public final static int ACTION_MOTO_AUTH = ACTION_MOTO_GROUP + 4;//订购预授权
    public final static int ACTION_MOTO_CANCEL = ACTION_MOTO_GROUP + 5;//订购预授权撤销
    public final static int ACTION_MOTO_AUTH_COMPLETE = ACTION_MOTO_GROUP + 6;//订购预授权完成请求
    public final static int ACTION_MOTO_AUTH_SETTLEMENT = ACTION_MOTO_GROUP + 7;//订购预授权完成通知
    public final static int ACTION_MOTO_COMPLETE_VOID = ACTION_MOTO_GROUP + 8;//订购预授权完成撤销
    public final static int ACTION_MOTO_VERIFY = ACTION_MOTO_GROUP + 9;//订购持卡人身份验证
    /**
     * 磁条卡充值
     */
    public final static int ACTION_ACCOUNT_LOAD_GROUP = MENU_OTHER + 900;
    public final static int ACTION_ACCOUNT_LOAD_CASH = ACTION_ACCOUNT_LOAD_GROUP + 1;//现金充值
    public final static int ACTION_ACCOUNT_LOAD_ACCOUNT = ACTION_ACCOUNT_LOAD_GROUP + 2;//账户充值
    public final static int ACTION_ACCOUNT_LOAD_CONFIRM = ACTION_ACCOUNT_LOAD_GROUP + 3;//账户确认
    /**
     * 管理
     */
    public final static int ACTION_SIGN_GROUP = MENU_ADMIN + 100;//签到
    public final static int ACTION_SIGN_OUT = MENU_ADMIN + 2;//签退
    public final static int ACTION_QUERY_TRANSACTION = MENU_ADMIN + 3;//交易查询
    public final static int ACTION_OPER_MANAGEMENT = MENU_ADMIN + 4;//柜员
    public final static int ACTION_EXTERNAL_NUMBER = MENU_ADMIN + 5;//外线号码
    public final static int ACTION_SETTLEMENT = MENU_ADMIN + 6;//结算
    public final static int ACTION_LOCK_TERMINAL = MENU_ADMIN + 7;//锁定终端
    public final static int ACTION_VERSION = MENU_ADMIN + 8;//版本
    /**
     * 签到
     */
    public final static int ACTION_SIGN_POS = ACTION_SIGN_GROUP + 1;//POS签到
    public final static int ACTION_SIGN_OPER = ACTION_SIGN_GROUP + 2;//操作员签到
    public final static int ACTION_SIGN_BONUS = ACTION_SIGN_GROUP + 3;//收银员积分签到

    /**
     * 设置菜单
     */
    public final static int ACTION_SETTING_GROUP = 20000;
    public final static int ACTION_SET_MERCHANT = ACTION_SETTING_GROUP + 1;
    public final static int ACTION_SET_TRANSACTION = ACTION_SETTING_GROUP + 2;
    public final static int ACTION_SET_SYSTEM = ACTION_SETTING_GROUP + 3;
    public final static int ACTION_SET_COMMUNICATION = ACTION_SETTING_GROUP + 4;
    public final static int ACTION_SET_KEY = ACTION_SETTING_GROUP + 5;
    public final static int ACTION_SET_PASSWORD = ACTION_SETTING_GROUP + 6;
    public final static int ACTION_SET_OTHER_GROUP = ACTION_SETTING_GROUP + 7;

    /**
     * 交易查询
     */
    public final static int ACTION_QUERY_TRANSACTION_GROUP = MENU_OTHER + 1000;
    public final static int ACTION_QUERY_TRANSACTION_DETAIL = ACTION_QUERY_TRANSACTION_GROUP + 1;//查询交易明细
    public final static int ACTION_QUERY_TRANSACTION_TOTAL = ACTION_QUERY_TRANSACTION_GROUP + 2;//查询交易汇总
    public final static int ACTION_QUERY_TRANSACTION_TRACE = ACTION_QUERY_TRANSACTION_GROUP + 3;//按凭证号查询
    public final static int ACTION_QUERY_TRANSACTION_CARDNO = ACTION_QUERY_TRANSACTION_GROUP + 4;//按卡号查询

    /**
     * 批上送
     */
    public final static int ACTION_BATCH_UP = 30000;//批上送
    public final static int ACTION_BATCH_UP_FINANCIAL_TRANS = ACTION_BATCH_UP + 1;//批上送金融交易/批上送结束
    public final static int ACTION_BATCH_UP_IC_SETTLEMENT = ACTION_BATCH_UP + 2;//9.4.7 基于 PBOC 借/贷记 IC 卡批上送通知交易


    /**
     * 测试
     */
    public final static int ACTION_ECHO_TEST = ACTION_SETTING_GROUP + 8;//回响测试
    public final static int ACTION_EXCHANGE_PARAMETER = ACTION_SETTING_GROUP + 9;//参数传递
    public final static int ACTION_DOWNLOAD_ICKEY = ACTION_SETTING_GROUP + 10;//下载ic卡公钥
    public final static int ACTION_DOWNLOAD_ICPARAMETER = ACTION_SETTING_GROUP + 11;//下载ic参数
    public final static int ACTION_UPLOAD_STATUS = ACTION_SETTING_GROUP + 12;//POS状态上送
    public final static int ACTION_DOWNLOAD_CARDBIN = ACTION_SETTING_GROUP + 13;//卡片名单下载
    public final static int ACTION_QUERY_ICKEY = ACTION_SETTING_GROUP + 14;//查询ic卡公钥
    public final static int ACTION_QUERY_NEXT_ICKEY = ACTION_SETTING_GROUP + 15;//继续查询ic卡公钥
    public final static int ACTION_DOWNLOAD_FINISH_ICKEY = ACTION_SETTING_GROUP + 16;//ic卡公钥下载结束
    public final static int ACTION_QUERY_ICPARAMS = ACTION_SETTING_GROUP + 17;//ic卡参数查询
    public final static int ACTION_QUERY_NEXT_PARAMS = ACTION_SETTING_GROUP + 18;//继续查询ic卡参数
    public final static int ACTION_DOWNLOAD_FINISH_PAMRAMS = ACTION_SETTING_GROUP + 19;//ic卡参数下载结束

    /**
     * 特色交易类
     */
    public final static int CHARACTERISTICS_OF_TRANSACTIONAL_GROUP = 40001;
    public final static int ACTION_INDUSTRY_TRANSFER = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 1;//行内转账
    public final static int ACTION_AGREED_AN_INTER_BANK_TRANSFER = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 2;// 跨行约定转账
    public final static int ACTION_AN_INTER_BANK_TRANSFER_NOT_AGREED = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 3;// 跨行非约定转账
    public final static int ACTION_REAL_TIME_COLLECTING_INTERFACE = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 4;// 实时代付接口
    public final static int ACTION_REAL_TIME_PAYMENT_CONFIRMATION_INTERFACE = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 5;// 实时代付确认接口
    public final static int ACTION_WITH_THE_QUERY_LETTER_AGREEMENT = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 6;// 用信协议查询
    public final static int ACTION_WITH_THE_LETTER_TO_APPLY_FOR = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 7;// 用信申请
    public final static int ACTION_REPAYMENT_BEFOREHAND_QUERY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 8;// 还款预查询
    public final static int ACTION_REIMBURSEMENT = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 9;//还款
    public final static int ACTION_LOAN_LIST_QUERY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 10;//贷款列表查询
    public final static int ACTION_EXISTENTIAL = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 11;//存现
    public final static int ACTION_CASH = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 12;//取现
    public final static int ACTION_CASH_CIRCLE_INTERFACE = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 13;//现金圈提接口
    public final static int ACTION_CIRCLE_OF_CASH_CONFIRMATION_INTERFACE = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 14;//现金圈提确认接口
    public final static int ACTION_CURRENT_TRANSFER_TIME = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 15;//活期转定期
    public final static int ACTION_TURN_CHECKING_REGULARLY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 16;//定期转活期
    public final static int ACTION_SMALL_CIRCULAR_MACHINE_DETAILED_QUERY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 17;// 小额循环机明细查询
    public final static int ACTION_THE_PASSBOOK_TO_FILL = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 18;//存折补登
    public final static int ACTION_UNDERSTAND_FOLD_TO_FILL = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 19;//明白折补登
    public final static int ACTION_ACCOUNT_VERIFICATION = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 20;//账户验证
    public final static int ACTION_TO_HELP_FARMERS_TRANSFER = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 21;//助农转账
    public final static int ACTION_BALANCE_INQUIRY_ON_A_REGULAR_BASIS = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 22;//定期余额查询
    public final static int ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 23;//他行助农取款
    public final static int ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS_RUSHED_WITHDRAWAL_IS = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 24;//他行助农取款冲正
    public final static int ACTION_VIOLATIONS_OF_INFORMATION_QUERY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 25;//违章罚款信息查询
    public final static int ACTION_VIOLATIONS_PAY_COST = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 26;//违章罚款缴费
    public final static int ACTION_VIOLATIONS_TO_EXPEND_THE_INFORMATION_QUERY = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 27;//违章罚款缴费结果信息查询
    public final static int ACTION_INQUIRY_OF_THE_PAYMENT = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 28;//购房付款查询
    public final static int ACTION_A_PAYMENT_SUCCESS = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 29;//付款成功通知
    public final static int ACTION_BALANCE_INFORMATION_NOTICE = CHARACTERISTICS_OF_TRANSACTIONAL_GROUP + 30;//余额信息通知






    public static String getAction(int action, boolean isC) {
        switch (action) {
            case ActionConstant.ACTION_SALE://消费
                return isC ? TLog.getString(R.string.action_consumption) : "SALE";
            case ActionConstant.ACTION_VOID://撤销
                return isC ? TLog.getString(R.string.action_revoke) : "VOID";
            case ActionConstant.ACTION_REFUND://退货
                return isC ? TLog.getString(R.string.action_refund) : "REFUND";
            case ActionConstant.ACTION_AUTH://预授权
                return isC ? TLog.getString(R.string.action_authorization_trading) : "AUTH";
            case ActionConstant.ACTION_CANCEL://预授权撤销
                return isC ? TLog.getString(R.string.action_authorization_undo) : "CANCEL";
            case ActionConstant.ACTION_AUTH_SETTLEMENT://预授权完成(通知)
                return isC ? TLog.getString(R.string.action_authorization_over_notice) : "AUTH SETTLEMENT";
            case ActionConstant.ACTION_AUTH_COMPLETE://预授权完成(请求)
                return isC ? TLog.getString(R.string.action_authorization_over_request) : "AUTH COMPLETE";
            case ActionConstant.ACTION_COMPLETE_VOID://预授权完成撤销
                return isC ? TLog.getString(R.string.action_authorization_over_undo) : "COMPLETE VOID";
            case ActionConstant.ACTION_QUERY_BALANCE://查询余额
                return isC ? TLog.getString(R.string.action_query_balance) : "QUERY BALANCE";
            case ActionConstant.ACTION_PRINT_LAST://重打印最后一笔
                return isC ? TLog.getString(R.string.action_print_next) : "PRINT LAST";
            case ActionConstant.ACTION_PRINT_RANDOM://重打印任意一笔
                return isC ? TLog.getString(R.string.action_print_any) : "PRINT RANDOM";
            case ActionConstant.ACTION_PRINT_DETAIL://打印交易明细
                return isC ? TLog.getString(R.string.action_print_detail) : "PRINT DETAIL";
            case ActionConstant.ACTION_PRINT_SUMMARY://打印交易汇总
                return isC ? TLog.getString(R.string.action_print_summary) : "PRINT SUMMARY";
            case ActionConstant.ACTION_PRINT_SETTLEMENT://重打印结算单
                return isC ? TLog.getString(R.string.action_print_settlement) : "PRINT SETTLEMENT";
            case ActionConstant.ACTION_OFFLINE://离线结算
                return isC ? TLog.getString(R.string.action_offline_settlement) : "OFFLINE SETTLEMENT";
            case ActionConstant.ACTION_ADJUST://离线调整
                return isC ? TLog.getString(R.string.action_offline_adjust) : "OFFLINE ADJUST";
            //-----管理界面下

            //---------电子现金
            case ActionConstant.ACTION_EC_GROUP://电子现金
                return isC ? TLog.getString(R.string.action_electronic_cash) : "EC GROUP";
            case ActionConstant.ACTION_EC_QUICKPASS://快速支付
                return isC ? TLog.getString(R.string.action_electronic_cash_fast_pay) : "EC QUICKPASS";
            case ActionConstant.ACTION_EC_SALE://普通支付
                return isC ? TLog.getString(R.string.action_electronic_cash_ordinary_pay) : "";
            case ActionConstant.ACTION_EC_LOAD_GROUP://普通支付
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun) : "";
            case ActionConstant.ACTION_EC_QUERY_BALANCE://余额查询
                return isC ? TLog.getString(R.string.action_electronic_cash_query_balance) : "";
            case ActionConstant.ACTION_EC_QUERY_DETAIL://明显查询
                return isC ? TLog.getString(R.string.action_electronic_cash_query_detail) : "";
            case ActionConstant.ACTION_EC_REFUND://脱机退货
                return isC ? TLog.getString(R.string.action_electronic_cash_offline_return) : "";
            case ActionConstant.ACTION_EC_LOAD_LOG://脱机退货
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun_log) : "";
            ///电子现金下 圈存
            case ActionConstant.ACTION_ECLOAD_CASH://
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun_up) : "";
            case ActionConstant.ACTION_ECLOAD_ACCOUNT://
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun_specify) : "";
            case ActionConstant.ACTION_ECLOAD_NONACCOUNT://
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun_unspecify) : "";
            case ActionConstant.ACTION_ECLOAD_CASH_VOID://
                return isC ? TLog.getString(R.string.action_electronic_cash_quancun_up_undo) : "";
            //分期付款
            case ActionConstant.ACTION_INSTALLMENT_GROUP://
                return isC ? TLog.getString(R.string.action_installment) : "";
            case ActionConstant.ACTION_INSTALLMENT://
                return isC ? TLog.getString(R.string.action_installment_consumption) : "";
            case ActionConstant.ACTION_INSTALLMENT_VOID://
                return isC ? TLog.getString(R.string.action_installment_consumption_undo) : "";
            //积分
            case ActionConstant.ACTION_BONUS_GROUP://
                return isC ? TLog.getString(R.string.action_integral) : "";
            case ActionConstant.ACTION_BONUS://
                return isC ? TLog.getString(R.string.action_integral_consumption) : "";
            case ActionConstant.ACTION_BONUS_VOID://
                return isC ? TLog.getString(R.string.action_integral_consumption_revoke) : "";
            case ActionConstant.ACTION_CUPBONUS_QUERY://
                return isC ? TLog.getString(R.string.action_integral_query) : "";
            case ActionConstant.ACTION_CUPBONUS_REFUND://
                return isC ? TLog.getString(R.string.action_integral_return) : "";
            //手机芯片
            case ActionConstant.ACTION_UPCARD_GROUP://
                return isC ? TLog.getString(R.string.action_chip) : "";
            case ActionConstant.ACTION_UPCARD://
                return isC ? TLog.getString(R.string.action_chip_consumption) : "";
            case ActionConstant.ACTION_UPCARD_VOID://
                return isC ? TLog.getString(R.string.action_chip_consumption_return) : "";
            case ActionConstant.ACTION_UPCARD_REFUND://
                return isC ? TLog.getString(R.string.action_chip_return) : "";
            case ActionConstant.ACTION_UPCARD_AUTH://
                return isC ? TLog.getString(R.string.action_chip_authorization) : "";
            case ActionConstant.ACTION_UPCARD_CANCEL://
                return isC ? TLog.getString(R.string.action_chip_authorization_undo) : "";
            case ActionConstant.ACTION_UPCARD_AUTH_COMPLETE://
                return isC ? TLog.getString(R.string.action_chip_authorization_over_request) : "";
            case ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT://
                return isC ? TLog.getString(R.string.action_chip_authorization_over_notice) : "";
            case ActionConstant.ACTION_UPCARD_COMPLETE_VOID://
                return isC ? TLog.getString(R.string.action_chip_authorization_over_undo) : "";
            case ActionConstant.ACTION_UPCARD_QUERY_BALANCE://
                return isC ? TLog.getString(R.string.action_chip_query_balance) : "";
            //预约消费
            case ActionConstant.ACTION_RESERVATION_GROUP://
                return isC ? TLog.getString(R.string.action_appointment) : "";
            case ActionConstant.ACTION_RESERVATION_SALE://
                return isC ? TLog.getString(R.string.action_appointment_consumption) : "";
            case ActionConstant.ACTION_RESERVATION_VOID://
                return isC ? TLog.getString(R.string.action_appointment_consumption_undo) : "";
            //订购交易
            case ActionConstant.ACTION_MOTO_GROUP://
                return isC ? TLog.getString(R.string.action_order) : "";
            case ActionConstant.ACTION_MOTO_SALE://
                return isC ? TLog.getString(R.string.action_order_consumption) : "";
            case ActionConstant.ACTION_MOTO_VOID://
                return isC ? TLog.getString(R.string.action_order_consumption_undo) : "";
            case ActionConstant.ACTION_MOTO_REFUND://
                return isC ? TLog.getString(R.string.action_order_return) : "";
            case ActionConstant.ACTION_MOTO_AUTH://
                return isC ? TLog.getString(R.string.action_order_authorization) : "";
            case ActionConstant.ACTION_MOTO_CANCEL://
                return isC ? TLog.getString(R.string.action_order_authorization_undo) : "";
            case ActionConstant.ACTION_MOTO_AUTH_COMPLETE://
                return isC ? TLog.getString(R.string.action_order_authorization_over_request) : "";
            case ActionConstant.ACTION_MOTO_AUTH_SETTLEMENT://
                return isC ? TLog.getString(R.string.action_order_authorization_over_notice) : "";
            case ActionConstant.ACTION_MOTO_COMPLETE_VOID://
                return isC ? TLog.getString(R.string.action_order_authorization_over_undo) : "";
            case ActionConstant.ACTION_MOTO_VERIFY://
                return isC ? TLog.getString(R.string.action_order_cardholder_verification) : "";
            //磁条卡充值
            case ActionConstant.ACTION_ACCOUNT_LOAD_GROUP://
                return isC ? TLog.getString(R.string.action_stripe_cards) : "";
            case ActionConstant.ACTION_ACCOUNT_LOAD_CASH://
                return isC ? TLog.getString(R.string.action_stripe_cards_rechargeable_cash) : "";
            case ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT://
                return isC ? TLog.getString(R.string.action_stripe_cards_rechargeable_account) : "";
            //管理
            case ActionConstant.ACTION_SIGN_GROUP://
                return isC ? TLog.getString(R.string.action_sign) : "";
            case ActionConstant.ACTION_SIGN_OUT://
                return isC ? TLog.getString(R.string.action_sign_out) : "";
            case ActionConstant.ACTION_QUERY_TRANSACTION://
                return isC ? TLog.getString(R.string.action_query_transaction) : "";
            case ActionConstant.ACTION_OPER_MANAGEMENT://
                return isC ? TLog.getString(R.string.action_teller) : "";
            case ActionConstant.ACTION_EXTERNAL_NUMBER://
                return isC ? TLog.getString(R.string.action_outside_number) : "";
            case ActionConstant.ACTION_SETTLEMENT://
                return isC ? TLog.getString(R.string.action_settlement) : "";
            case ActionConstant.ACTION_LOCK_TERMINAL://
                return isC ? TLog.getString(R.string.action_lock_terminal) : "";
            case ActionConstant.ACTION_VERSION://
                return isC ? TLog.getString(R.string.action_version) : "";
            //签到
            case ActionConstant.ACTION_SIGN_POS://
                return isC ? TLog.getString(R.string.action_sign_pos) : "";
            case ActionConstant.ACTION_SIGN_OPER://
                return isC ? TLog.getString(R.string.action_sign_teller) : "";
            case ActionConstant.ACTION_SIGN_BONUS://
                return isC ? TLog.getString(R.string.action_sign_integral) : "";
            //设置界面菜单
            case ActionConstant.ACTION_SET_MERCHANT://
                return isC ? TLog.getString(R.string.action_setting_merchant) : "";
            case ActionConstant.ACTION_SET_TRANSACTION://
                return isC ? TLog.getString(R.string.action_setting_transaction_management) : "";
            case ActionConstant.ACTION_SET_SYSTEM://
                return isC ? TLog.getString(R.string.action_setting_system) : "";
            case ActionConstant.ACTION_SET_COMMUNICATION://
                return isC ? TLog.getString(R.string.action_setting_communication) : "";
            case ActionConstant.ACTION_SET_KEY://
                return isC ? TLog.getString(R.string.action_setting_key) : "";
            case ActionConstant.ACTION_SET_PASSWORD://
                return isC ? TLog.getString(R.string.action_setting_password) : "";
            case ActionConstant.ACTION_SET_OTHER_GROUP://
                return isC ? TLog.getString(R.string.action_setting_other) : "";
            case ActionConstant.ACTION_QUERY_TRANSACTION_DETAIL://
                return isC ? TLog.getString(R.string.action_query_detail) : "";
            case ActionConstant.ACTION_QUERY_TRANSACTION_TOTAL://
                return isC ? TLog.getString(R.string.action_query_summary) : "";
            case ActionConstant.ACTION_QUERY_TRANSACTION_TRACE://
                return isC ? TLog.getString(R.string.action_query_for_serial) : "";
            case ActionConstant.ACTION_QUERY_TRANSACTION_CARDNO://
                return isC ? TLog.getString(R.string.action_query_for_card) : "";
            default:
                return isC ? TLog.getString(R.string.error_action) : "";
        }

    }


}
