package cn.basewin.unionpay.db;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.entity.ResponseCode;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/6 11:19<br>
 * 描述:  <br>
 */
public class ResponseCodeDao {
    public static List<ResponseCode> data = new ArrayList<>();

    static {
        data.add(new ResponseCode("00", "交易成功"));
        data.add(new ResponseCode("01", "请持卡人与发卡银行联系"));
        data.add(new ResponseCode("02", "通信接收失败"));
        data.add(new ResponseCode("03", "无效商户"));
        data.add(new ResponseCode("04", "此卡为无效卡"));
        data.add(new ResponseCode("05", "持卡人认证失败"));
        data.add(new ResponseCode("06", "发送主机失败"));
        data.add(new ResponseCode("07", "特殊条件下没收卡"));
        data.add(new ResponseCode("10", "显示部分批准金额，提示操作员"));
        data.add(new ResponseCode("11", "此为 VIP 客户"));
        data.add(new ResponseCode("12", "接收主机超时"));
        data.add(new ResponseCode("13", "无效金额"));
        data.add(new ResponseCode("14", "无效卡号"));
        data.add(new ResponseCode("15", "此卡无对应发卡方"));
        data.add(new ResponseCode("16", "更新第三磁道"));
        data.add(new ResponseCode("17", "拒绝但不没收卡"));
        data.add(new ResponseCode("1N", "卡号不存在"));
        data.add(new ResponseCode("21", "发送主机失败"));
        data.add(new ResponseCode("23", "交易金额错误"));
        data.add(new ResponseCode("25", "没有原始交易， 请联系发卡方"));
        data.add(new ResponseCode("26", "原交易的状态为失败"));
        data.add(new ResponseCode("30", "上送的查询标记错误， 大于数据库中的详细信息总数"));
        data.add(new ResponseCode("31", "未配置本银行账户"));
        data.add(new ResponseCode("33", "请重试"));
        data.add(new ResponseCode("34", "作弊卡，吞卡"));
        data.add(new ResponseCode("35", "受卡方与代理方联系（没收卡）"));
        data.add(new ResponseCode("37", "受卡方电话通知代理方安全部门"));
        data.add(new ResponseCode("39", "无贷记账户"));
        data.add(new ResponseCode("40", "发卡方不支持的交易类型"));
        data.add(new ResponseCode("41", "此卡已挂失"));
        data.add(new ResponseCode("42", "无此账户"));
        data.add(new ResponseCode("43", "被窃卡"));
        data.add(new ResponseCode("44", "无此投资账户"));
        data.add(new ResponseCode("51", "卡余额不足"));
        data.add(new ResponseCode("52", "无此支票账户"));
        data.add(new ResponseCode("53", "无此储蓄卡账户"));
        data.add(new ResponseCode("54", "过期的卡"));
        data.add(new ResponseCode("55", "密码错"));
        data.add(new ResponseCode("56", "无此卡号"));
        data.add(new ResponseCode("57", "不允许此卡交易接口设计说明书"));
        data.add(new ResponseCode("58", "发卡行不允许该卡在本终端进行交易"));
        data.add(new ResponseCode("59", "该账户已销户"));
        data.add(new ResponseCode("5M", "已退货或撤消"));
        data.add(new ResponseCode("61", "交易金额超限"));
        data.add(new ResponseCode("62", "受限制的卡"));
        data.add(new ResponseCode("63", "超出业务规定期限"));
        data.add(new ResponseCode("64", "交易金额与原交易不匹配"));
        data.add(new ResponseCode("65", "超出取款次数限制"));
        data.add(new ResponseCode("75", "密码错误次数超限"));
        data.add(new ResponseCode("76", "无效账户"));
        data.add(new ResponseCode("77", "超出业务规定期限"));
        data.add(new ResponseCode("81", "大额系统不可用"));
        data.add(new ResponseCode("82", "小额系统不可用"));
        data.add(new ResponseCode("91", "发卡方状态不正常， 请稍后重试"));
        data.add(new ResponseCode("92", "数据库异常"));
        data.add(new ResponseCode("93", "交易违法、不能完成"));
        data.add(new ResponseCode("94", "五分钟内不允许重复交易"));
        data.add(new ResponseCode("96", "拒绝，交换中心异常，请稍后重试"));
        data.add(new ResponseCode("97", "终端未登记"));
        data.add(new ResponseCode("98", "密码转加密失败"));
        data.add(new ResponseCode("99", "55域错误"));
        data.add(new ResponseCode("A0", "MAC计算错误"));
        data.add(new ResponseCode("A1", "转账货币不一致"));
        data.add(new ResponseCode("A2", "与主机通信超时"));
        data.add(new ResponseCode("A3", "资金到账行帐号不正确"));
        data.add(new ResponseCode("A5", "MAC生成错误"));
        data.add(new ResponseCode("A7", "拒绝，交换中心异常，请稍后重试"));
        data.add(new ResponseCode("A8", "终端未签到"));
        data.add(new ResponseCode("A9", "商户状态异常"));
        data.add(new ResponseCode("B1", "商户未绑定账户"));
        data.add(new ResponseCode("B2", "录入员非法"));
        data.add(new ResponseCode("B3", "交易失败,已冲正"));
        data.add(new ResponseCode("BA", "超时已冲正"));
        data.add(new ResponseCode("C1", "储蓄户不能转账"));
        data.add(new ResponseCode("C2", "此附属卡不能查询主卡"));
        data.add(new ResponseCode("C3", "本交易不支持无密存折，请到银行柜台"));
        data.add(new ResponseCode("CF", "交易失败"));
        data.add(new ResponseCode("CR", "交易失败"));
        data.add(new ResponseCode("D1", "机构代码错误"));
        data.add(new ResponseCode("D2", "日期错误"));
        data.add(new ResponseCode("D3", "无效的文件类型接口设计说明书"));
        data.add(new ResponseCode("D4", "已经处理过的文件"));
        data.add(new ResponseCode("D5", "无此文件"));
        data.add(new ResponseCode("D6", "接收者不支持"));
        data.add(new ResponseCode("D7", "文件锁定"));
        data.add(new ResponseCode("D8", "未成功"));
        data.add(new ResponseCode("D9", "文件长度不符"));
        data.add(new ResponseCode("DA", "异常错误"));
        data.add(new ResponseCode("DB", "处罚决定书号为空"));
        data.add(new ResponseCode("DC", "处罚决定书位数错误"));
        data.add(new ResponseCode("DD", "不在交易窗口"));
        data.add(new ResponseCode("DE", "重复缴费"));
        data.add(new ResponseCode("DF", "财政端无该票信息"));
        data.add(new ResponseCode("DH", "该票已收款"));
        data.add(new ResponseCode("DI", "该票所属地未开通交通罚款缴费业务"));
        data.add(new ResponseCode("DJ", "票据状态异常"));
        data.add(new ResponseCode("DK", "缴费关键信息异常"));
        data.add(new ResponseCode("DL", "罚款与滞纳金之和不等于缴费金额"));
        data.add(new ResponseCode("DM", "缴费金额超限"));
        data.add(new ResponseCode("DN", "缴费失败"));
        data.add(new ResponseCode("DO", "缴费超时，请核查交易流水"));
        data.add(new ResponseCode("DP", "无此符合条件记录"));
        data.add(new ResponseCode("DQ", "该处罚决定书缴费失败/处罚决定书号与缴费信息不一致"));
        data.add(new ResponseCode("E1", "数据库操作失败"));
        data.add(new ResponseCode("E2", "无此终端"));
        data.add(new ResponseCode("E3", "未找到对应的商户信息"));
        data.add(new ResponseCode("E4", "无存折补登明细信息"));
        data.add(new ResponseCode("E6", "安全模块号绑定错误"));
        data.add(new ResponseCode("EA", "渠道不支持该交易"));
        data.add(new ResponseCode("EB", "不受理贷记卡"));
        data.add(new ResponseCode("EC", "当天限额超限"));
        data.add(new ResponseCode("ED", "终端未启用"));
        data.add(new ResponseCode("EF", "卡bin未配置"));
        data.add(new ResponseCode("EG", "交易失败,未输入密码"));
        data.add(new ResponseCode("ER", "状态未知"));
        data.add(new ResponseCode("ES", "商户不支持该交易"));
        data.add(new ResponseCode("ET", "账号卡性质非法"));
        data.add(new ResponseCode("EW", "终端未签到或终端不存在"));
        data.add(new ResponseCode("F1", "文件记录格式错误"));
        data.add(new ResponseCode("F2", "文件记录重复"));
        data.add(new ResponseCode("F3", "文件记录不存在接口设计说明书"));
        data.add(new ResponseCode("F4", "文件记录错误"));
        data.add(new ResponseCode("F5", "文件批量转联机未完成"));
        data.add(new ResponseCode("FB", "超过最大命令个数"));
        data.add(new ResponseCode("FE", "数据库无记录错误"));
        data.add(new ResponseCode("GN", "不支持他行卡取现"));
        data.add(new ResponseCode("GP", "终端限额超限"));
        data.add(new ResponseCode("GQ", "转入卡须为他行卡"));
        data.add(new ResponseCode("GR", "代付卡必须是原卡"));
        data.add(new ResponseCode("GW", "无效卡号"));
        data.add(new ResponseCode("H7", "生成工作密钥失败"));
        data.add(new ResponseCode("H8", "解析TMK失败"));
        data.add(new ResponseCode("KK", "非当天撤销"));
        data.add(new ResponseCode("LC", "黑名单卡，不允许交易"));
        data.add(new ResponseCode("MC", "MAC生成错误"));
        data.add(new ResponseCode("ME", "MAC生成错"));
        data.add(new ResponseCode("N1", "未登折帐目已超限，交易不成功"));
        data.add(new ResponseCode("PI", "申请PIN失败"));
        data.add(new ResponseCode("RV", "接收失败"));
        data.add(new ResponseCode("SE", "发送主机失败"));
        data.add(new ResponseCode("TC", "请查看62域内容"));
        data.add(new ResponseCode("UW", "申请PIN工作密钥失败"));
        data.add(new ResponseCode("Y1", "脱机交易成功"));
        data.add(new ResponseCode("Y3", "不能联机，脱机交易成功"));
        data.add(new ResponseCode("Z1", "超过30天，不允许撤销"));
        data.add(new ResponseCode("Z2", "交易商户非原脱机消费商户"));
        data.add(new ResponseCode("Z3", "不能联机，脱机交易失败"));
        data.add(new ResponseCode("ZT", "未知错误"));
        data.add(new ResponseCode("ZW", "交易商户与原商户不符"));
        data.add(new ResponseCode("ZY", "未知错误"));
        data.add(new ResponseCode("ZZ", "数据库操作失败"));
    }

    public static String show(String _39) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCode().equalsIgnoreCase(_39)) {
                return data.get(i).getCode() + "\n\r" + data.get(i).getLog();
            }
        }
        return _39 + "\n\r" + "未知错误";
    }
}
