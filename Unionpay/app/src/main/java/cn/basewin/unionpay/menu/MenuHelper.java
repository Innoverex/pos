package cn.basewin.unionpay.menu;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.menu.action.AnnotationMenu;
import cn.basewin.unionpay.menu.action.MenuAction;
import cn.basewin.unionpay.setting.BONUSTypeSettingAty;
import cn.basewin.unionpay.setting.ECashTypeSettingAty;
import cn.basewin.unionpay.setting.InstallmentTypeSettingAty;
import cn.basewin.unionpay.setting.MOTOTypeSettingAty;
import cn.basewin.unionpay.setting.OtherTypeSettingAty;
import cn.basewin.unionpay.setting.ReservationTypeSettingAty;
import cn.basewin.unionpay.setting.TraditionalTypeSettingAty;
import cn.basewin.unionpay.setting.UpCardSettingAty;
import cn.basewin.unionpay.utils.AnnotationUtil;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 14:25<br>
 * 描述:  <br>
 */
public class MenuHelper {
    public static List<MenuAction> getMenuAction(int key, Activity aty) {
        int[] menus = getMenus(key);
        if (menus == null || menus.length <= 0) {
            if (aty != null) {
                aty.finish();
            }
            TLog.l("拿到菜单列表为空！");
            return null;
        }
        ArrayList<MenuAction> menuActions = new ArrayList<>();
        try {
            long l2 = System.currentTimeMillis();
            TLog.l(l2 + "");
            ArrayList<Class<?>> net = AnnotationUtil.net("cn.basewin.unionpay.menu", AnnotationMenu.class);
            if (net == null || net.size() <= 0) {
                if (aty != null) {
                    aty.finish();
                }
                TLog.l("扫描菜单模块为空！");
                return null;
            }
            long l = System.currentTimeMillis();
            TLog.l(l2 - l + "...");
            TLog.l(l + ".");
            for (int c : menus) {
                for (int i = 0; i < net.size(); i++) {
                    AnnotationMenu annotation = net.get(i).getAnnotation(AnnotationMenu.class);
                    if (annotation != null && annotation.action() == c) {
                        MenuAction menuAction = (MenuAction) net.get(i).newInstance();
                        menuAction.setContext(aty);
                        //add by hanlei 2016/09/22
                        menuAction.setAction(c);
                        menuActions.add(menuAction);

                    }
                }
            }
            long l1 = System.currentTimeMillis();
            TLog.l(l1 - l + "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return menuActions;
    }

    //根据外部调用意图，获取Runnable
    public static MenuAction getMenuByAction(int action, Activity aty) {
        ArrayList<MenuAction> menuActions = new ArrayList<>();
        try {
            ArrayList<Class<?>> net = AnnotationUtil.net("cn.basewin.unionpay.menu", AnnotationMenu.class);
            if (net == null || net.size() <= 0) {
                if (aty != null) {
                    aty.finish();
                }
                TLog.l("扫描菜单模块为空！");
                return null;
            }

            for (int i = 0; i < net.size(); i++) {
                AnnotationMenu annotation = net.get(i).getAnnotation(AnnotationMenu.class);
                if (annotation != null && annotation.action() == action) {
                    MenuAction menuAction = (MenuAction) net.get(i).newInstance();
                    menuAction.setContext(aty);
                    menuAction.setAction(action);
                    return menuAction;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 通过开启菜单action 拿到 这个菜单界面出现的功能按钮的action数组
     *
     * @param id
     * @return
     */
    public static int[] getMenus(int id) {
        int[] menus = null;
        switch (id) {
            case ActionConstant.MENU_MAIN:
                menus = new int[]{
                        TraditionalTypeSettingAty.isSale() ? ActionConstant.ACTION_SALE : 0,
                        TraditionalTypeSettingAty.isVoid() ? ActionConstant.ACTION_VOID : 0,
                        TraditionalTypeSettingAty.isRefund() ? ActionConstant.ACTION_REFUND : 0,
                        ActionConstant.MENU_AUTH,
                        ActionConstant.MENU_OFFLINE,
                        ActionConstant.MENU_PRINT,
                        ActionConstant.MENU_ADMIN,
                        ActionConstant.MENU_CHARACTERISTICS_OF_TRANSACTIONAL,
                        ActionConstant.MENU_OTHER
                        };
                break;
            case ActionConstant.MENU_AUTH: //预授权界面
                menus = MENU_PREAUTHORIZATION();
                break;
            case ActionConstant.MENU_OFFLINE: //离线界面
                menus = MENU_OFFLINE();
                break;
            case ActionConstant.MENU_PRINT: //打印界面
                menus = MENU_PRINT();
                break;
            case ActionConstant.MENU_ADMIN: //管理界面
                menus = MENU_ADMIN();
                break;
            case ActionConstant.MENU_OTHER: //其他界面
                menus = MENU_OTHER();
                break;
            case ActionConstant.MENU_CHARACTERISTICS_OF_TRANSACTIONAL: //特色交易界面
                menus = MENU_CHARACTERISTICS_OF_TRANSACTIONAL();
                break;
            //管理界面
            case ActionConstant.ACTION_SIGN_GROUP: //签到
                menus = ACTION_SIGN();
                break;
            //其他设置
            case ActionConstant.ACTION_EC_GROUP: //电子现金
                menus = ACTION_UPCASH();
                break;
            case ActionConstant.ACTION_EC_LOAD_GROUP: //电子现金下 圈存
                menus = ACTION_UPCASH_LOAD();
                break;
            case ActionConstant.ACTION_INSTALLMENT_GROUP: //分期付款
                menus = ACTION_INSTALLMENT();
                break;
            case ActionConstant.ACTION_BONUS_GROUP: //积分
                menus = ACTION_BONUS();
                break;
            case ActionConstant.ACTION_UPCARD_GROUP: //手机芯片
                menus = ACTION_UPCARD();
                break;
            case ActionConstant.ACTION_RESERVATION_GROUP: //预约消费
                menus = ACTION_RESERVATION();
                break;
            case ActionConstant.ACTION_MOTO_GROUP: //订购交易
                menus = ACTION_MOTO();
                break;
            case ActionConstant.ACTION_ACCOUNT_LOAD_GROUP: //磁条卡充值
                menus = ACTION_ACCOUNT_LOAD_GROUP();
                break;
            case ActionConstant.ACTION_SETTING_GROUP: //设置主界面
                menus = ACTION_SETTING();
                break;
            case ActionConstant.ACTION_QUERY_TRANSACTION_GROUP: //交易查询
                menus = ACTION_QUERY_TRANSACTION_GROUP();
                break;
            case ActionConstant.ACTION_BONUS_GROUP_BONUS: //积分消费菜单
                menus = new int[]{
                        BONUSTypeSettingAty.isBONUS() ? ActionConstant.ACTION_BONUS : 0,
                        BONUSTypeSettingAty.isCUPBONUS() ? ActionConstant.ACTION_CUPBONUS : 0
                };
                break;
            case ActionConstant.ACTION_BONUS_VOID_GROUP: //积分消费撤销
                menus = new int[]{
                        BONUSTypeSettingAty.isBONUS_VOID() ? ActionConstant.ACTION_BONUS_VOID : 0,
                        BONUSTypeSettingAty.isCUPBONUS_VOID() ? ActionConstant.ACTION_CUPBONUS_VOID : 0};
                break;
            case ActionConstant.MENU_TRANSFER: //
                menus = new int[]{
                        ActionConstant.ACTION_INDUSTRY_TRANSFER,
                        ActionConstant.ACTION_AGREED_AN_INTER_BANK_TRANSFER,
                        ActionConstant.ACTION_AN_INTER_BANK_TRANSFER_NOT_AGREED};
                break;
            case ActionConstant.MENU_PAID: //
                menus = new int[]{
                        ActionConstant.ACTION_REAL_TIME_COLLECTING_INTERFACE,
                        ActionConstant.ACTION_REAL_TIME_PAYMENT_CONFIRMATION_INTERFACE};
                break;
            case ActionConstant.MENU_CREDIT: //
                menus = new int[]{
                        ActionConstant.ACTION_WITH_THE_QUERY_LETTER_AGREEMENT,
                        ActionConstant.ACTION_WITH_THE_LETTER_TO_APPLY_FOR,
                        ActionConstant.ACTION_REPAYMENT_BEFOREHAND_QUERY,
                        ActionConstant.ACTION_REIMBURSEMENT,
                        ActionConstant.ACTION_LOAN_LIST_QUERY};
                break;
            case ActionConstant.MENU_CASH_AROUND_TO_ASK: //
                menus = new int[]{
                        ActionConstant.ACTION_EXISTENTIAL,
                        ActionConstant.ACTION_CASH,
                        ActionConstant.ACTION_CASH_CIRCLE_INTERFACE,
                        ActionConstant.ACTION_CIRCLE_OF_CASH_CONFIRMATION_INTERFACE};
                break;
            case ActionConstant.MENU_PASSBOOK: //
                menus = new int[]{
                        ActionConstant.ACTION_CURRENT_TRANSFER_TIME,
                        ActionConstant.ACTION_TURN_CHECKING_REGULARLY,
                        ActionConstant.ACTION_SMALL_CIRCULAR_MACHINE_DETAILED_QUERY,
                        ActionConstant.ACTION_THE_PASSBOOK_TO_FILL,
                        ActionConstant.ACTION_UNDERSTAND_FOLD_TO_FILL,
                        ActionConstant.ACTION_ACCOUNT_VERIFICATION};
                break;
            case ActionConstant.MENU_HELP_FARMERS: //
                menus = new int[]{
                        ActionConstant.ACTION_TO_HELP_FARMERS_TRANSFER,
                        ActionConstant.ACTION_BALANCE_INQUIRY_ON_A_REGULAR_BASIS,
                        ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS,
                        ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS_RUSHED_WITHDRAWAL_IS};
                break;
            case ActionConstant.MENU_BILL_AYMENT: //
                menus = new int[]{
                        ActionConstant.ACTION_VIOLATIONS_OF_INFORMATION_QUERY,
                        ActionConstant.ACTION_VIOLATIONS_PAY_COST,
                        ActionConstant.ACTION_VIOLATIONS_TO_EXPEND_THE_INFORMATION_QUERY,
                        ActionConstant.ACTION_INQUIRY_OF_THE_PAYMENT,
                        ActionConstant.ACTION_A_PAYMENT_SUCCESS,
                        ActionConstant.ACTION_BALANCE_INFORMATION_NOTICE};
                break;
        }
        return menus;
    }

    private static int[] MENU_CHARACTERISTICS_OF_TRANSACTIONAL() {
        return new int[]{ActionConstant.MENU_TRANSFER,
                ActionConstant.MENU_PAID,
                ActionConstant.MENU_CREDIT,
                ActionConstant.MENU_CASH_AROUND_TO_ASK,
                ActionConstant.MENU_PASSBOOK,
                ActionConstant.MENU_HELP_FARMERS,
                ActionConstant.MENU_BILL_AYMENT
        };
    }

    /**
     * 设置主界面
     *
     * @return
     */
    private static int[] ACTION_SETTING() {
        return new int[]{
                ActionConstant.ACTION_SET_MERCHANT,
                ActionConstant.ACTION_SET_TRANSACTION,
                ActionConstant.ACTION_SET_SYSTEM,
                ActionConstant.ACTION_SET_COMMUNICATION,
                ActionConstant.ACTION_SET_KEY,
                ActionConstant.ACTION_SET_PASSWORD,
                ActionConstant.ACTION_SET_OTHER_GROUP
        };
    }

    /**
     * 预授权菜单
     *
     * @return
     */
    private static int[] MENU_PREAUTHORIZATION() {
        return new int[]{
                TraditionalTypeSettingAty.isAuth() ? ActionConstant.ACTION_AUTH : 0,
                TraditionalTypeSettingAty.isAuthComplete() ? ActionConstant.ACTION_AUTH_COMPLETE : 0,
                TraditionalTypeSettingAty.isAuthSettlement() ? ActionConstant.ACTION_AUTH_SETTLEMENT : 0,
                TraditionalTypeSettingAty.isCancel() ? ActionConstant.ACTION_CANCEL : 0,
                TraditionalTypeSettingAty.isCompleteVoid() ? ActionConstant.ACTION_COMPLETE_VOID : 0
        };
    }

    /**
     * 离线
     *
     * @return
     */
    private static int[] MENU_OFFLINE() {
        return new int[]{
                TraditionalTypeSettingAty.isOffline() ? ActionConstant.ACTION_OFFLINE : 0,
                TraditionalTypeSettingAty.isAdjust() ? ActionConstant.ACTION_ADJUST : 0
        };
    }

    /**
     * 打印
     *
     * @return
     */
    private static int[] MENU_PRINT() {
        return new int[]{ActionConstant.ACTION_PRINT_LAST,
                ActionConstant.ACTION_PRINT_RANDOM,
                ActionConstant.ACTION_PRINT_DETAIL,
                ActionConstant.ACTION_PRINT_SUMMARY,
                ActionConstant.ACTION_PRINT_SETTLEMENT};
    }

    /**
     * 管理菜单
     *
     * @return
     */
    private static int[] MENU_ADMIN() {
        return new int[]{ActionConstant.ACTION_SIGN_GROUP,
                ActionConstant.ACTION_SIGN_OUT,
                ActionConstant.ACTION_QUERY_TRANSACTION,
                ActionConstant.ACTION_OPER_MANAGEMENT,
                ActionConstant.ACTION_EXTERNAL_NUMBER,
                ActionConstant.ACTION_SETTLEMENT,
                ActionConstant.ACTION_LOCK_TERMINAL,
                ActionConstant.ACTION_VERSION
        };
    }

    /**
     * 签到菜单
     *
     * @return
     */
    private static int[] ACTION_SIGN() {
        return new int[]{
                ActionConstant.ACTION_SIGN_POS,
                ActionConstant.ACTION_SIGN_OPER,
                ActionConstant.ACTION_SIGN_BONUS};
    }

    /**
     * 菜单 其他
     *
     * @return
     */
    private static int[] MENU_OTHER() {
        return new int[]{ActionConstant.ACTION_EC_GROUP,
                ActionConstant.ACTION_INSTALLMENT_GROUP,
                ActionConstant.ACTION_QUERY_BALANCE, //余额
                ActionConstant.ACTION_BONUS_GROUP,
                ActionConstant.ACTION_RESERVATION_GROUP,
                ActionConstant.ACTION_MOTO_GROUP,
                ActionConstant.ACTION_ACCOUNT_LOAD_GROUP
        };
    }

    /**
     * 其他--电子现金
     *
     * @return
     */
    private static int[] ACTION_UPCASH() {
        return new int[]{
                ECashTypeSettingAty.isECquickpass() ? ActionConstant.ACTION_EC_QUICKPASS : 0,
                ECashTypeSettingAty.isECsale() ? ActionConstant.ACTION_EC_SALE : 0,
                ActionConstant.ACTION_EC_LOAD_GROUP,
                ActionConstant.ACTION_EC_QUERY_BALANCE,
                ActionConstant.ACTION_EC_QUERY_DETAIL,
                ECashTypeSettingAty.isECrefund() ? ActionConstant.ACTION_EC_REFUND : 0,
                ActionConstant.ACTION_EC_LOAD_LOG
        };
    }

    /**
     * 电子现金下 圈存
     *
     * @return
     */
    private static int[] ACTION_UPCASH_LOAD() {
        return new int[]{
                ECashTypeSettingAty.isECloadCash() ? ActionConstant.ACTION_ECLOAD_CASH : 0,
                ECashTypeSettingAty.isECloadAccount() ? ActionConstant.ACTION_ECLOAD_ACCOUNT : 0,
                ECashTypeSettingAty.isECloadNonaccount() ? ActionConstant.ACTION_ECLOAD_NONACCOUNT : 0,
                ECashTypeSettingAty.isECloadCashVoid() ? ActionConstant.ACTION_ECLOAD_CASH_VOID : 0
        };
    }


    /**
     * 分期付款
     *
     * @return
     */
    private static int[] ACTION_INSTALLMENT() {
        return new int[]{
                InstallmentTypeSettingAty.isInstallment() ? ActionConstant.ACTION_INSTALLMENT : 0,
                InstallmentTypeSettingAty.isInstallmentVoid() ? ActionConstant.ACTION_INSTALLMENT_VOID : 0
        };
    }

    /**
     * 积分 交易
     *
     * @return
     */
    private static int[] ACTION_BONUS() {
        return new int[]{
                ActionConstant.ACTION_BONUS_GROUP_BONUS,
                ActionConstant.ACTION_BONUS_VOID_GROUP,
                BONUSTypeSettingAty.isCUPBONUS_QUERY() ? ActionConstant.ACTION_CUPBONUS_QUERY : 0,
                BONUSTypeSettingAty.isCUPBONUS_REFUND() ? ActionConstant.ACTION_CUPBONUS_REFUND : 0
        };
    }

    /**
     * 手机芯片
     *
     * @return
     */
    private static int[] ACTION_UPCARD() {
        return new int[]{
                UpCardSettingAty.isUPCARD() ? ActionConstant.ACTION_UPCARD : 0,
                UpCardSettingAty.isUPCARD_VOID() ? ActionConstant.ACTION_UPCARD_VOID : 0,
                UpCardSettingAty.isUPCARD_REFUND() ? ActionConstant.ACTION_UPCARD_REFUND : 0,
                UpCardSettingAty.isUPCARD_AUTH() ? ActionConstant.ACTION_UPCARD_AUTH : 0,
                ActionConstant.ACTION_UPCARD_CANCEL,
                ActionConstant.ACTION_UPCARD_AUTH_COMPLETE,
                ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT,
                ActionConstant.ACTION_UPCARD_COMPLETE_VOID,
                ActionConstant.ACTION_UPCARD_QUERY_BALANCE
        };
    }

    /**
     * 预约消费
     *
     * @return
     */
    private static int[] ACTION_RESERVATION() {
        return new int[]{
                ReservationTypeSettingAty.isRESERVATION_SALE() ? ActionConstant.ACTION_RESERVATION_SALE : 0,
                ReservationTypeSettingAty.isRESERVATION_VOID() ? ActionConstant.ACTION_RESERVATION_VOID : 0
        };
    }

    /**
     * 订购交易
     *
     * @return
     */
    private static int[] ACTION_MOTO() {
        return new int[]{
                MOTOTypeSettingAty.isMotoSale() ? ActionConstant.ACTION_MOTO_SALE : 0,
                MOTOTypeSettingAty.isMotoVoid() ? ActionConstant.ACTION_MOTO_VOID : 0,
                MOTOTypeSettingAty.isMotoRefund() ? ActionConstant.ACTION_MOTO_REFUND : 0,
                MOTOTypeSettingAty.isMotoAuth() ? ActionConstant.ACTION_MOTO_AUTH : 0,
                MOTOTypeSettingAty.isMotoCancel() ? ActionConstant.ACTION_MOTO_CANCEL : 0,
                MOTOTypeSettingAty.isMotoAuthComplete() ? ActionConstant.ACTION_MOTO_AUTH_COMPLETE : 0,
                MOTOTypeSettingAty.isMotoAuthSettlement() ? ActionConstant.ACTION_MOTO_AUTH_SETTLEMENT : 0,
                MOTOTypeSettingAty.isMotoCompleteVoid() ? ActionConstant.ACTION_MOTO_COMPLETE_VOID : 0,
                MOTOTypeSettingAty.isMotoVerify() ? ActionConstant.ACTION_MOTO_VERIFY : 0
        };
    }

    /**
     * 磁条卡充值
     *
     * @return
     */
    private static int[] ACTION_ACCOUNT_LOAD_GROUP() {
        return new int[]{
                OtherTypeSettingAty.isACCOUNT_LOAD_CASH() ? ActionConstant.ACTION_ACCOUNT_LOAD_CASH : 0,
                OtherTypeSettingAty.isACCOUNT_LOAD_ACCOUNT() ? ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT : 0
        };
    }

    /**
     * 交易查询
     *
     * @return
     */
    private static int[] ACTION_QUERY_TRANSACTION_GROUP() {
        return new int[]{
                ActionConstant.ACTION_QUERY_TRANSACTION_DETAIL,
                ActionConstant.ACTION_QUERY_TRANSACTION_TOTAL,
                ActionConstant.ACTION_QUERY_TRANSACTION_TRACE,
                ActionConstant.ACTION_QUERY_TRANSACTION_CARDNO
        };
    }
}
