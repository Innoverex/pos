package cn.basewin.unionpay;

/**
 * 创建时间:  2016/7/20 17:43<br>
 * 描述: 错误码 <br>
 */
public class ErrorConstant {
    //数据库 错误
    //网络 错误
    //扫描 错误
    public static int error_scan_null = 0;

    //后台返回错误代码	终端提示内容（推荐）
    public static int _00 = 0x00;//	交易成功
    public static int _01 = 0x01;//	请持卡人与发卡银行联系
    public static int _03 = 0x03;//	无效商户
    public static int _04 = 0x04;//	此卡为无效卡（POS）
    public static int _05 = 0x05;//	持卡人认证失败
    public static int _10 = 0x10;//	显示部分批准金额，提示操作员
    public static int _11 = 0x11;//	此为VIP客户
    public static int _12 = 0x12;//	无效交易
    public static int _13 = 0x13;//	无效金额
    public static int _14 = 0x14;//	无效卡号
    public static int _15 = 0x15;//	此卡无对应发卡方
    public static int _21 = 0x21;//	该卡未初始化或睡眠卡
    public static int _22 = 0x22;//	操作有误，或超出交易允许天数
    public static int _25 = 0x25;//	没有原始交易，请联系发卡方
    public static int _30 = 0x30;//	请重试
    public static int _34 = 0x34;//	作弊卡,呑卡
    public static int _38 = 0x38;//	密码错误次数超限，请与发卡方联系
    public static int _40 = 0x40;//	发卡方不支持的交易
    public static int _41 = 0x41;//	挂失卡（POS）
    public static int _43 = 0x43;//	被窃卡（POS）
    public static int _45 = 0x45;//	请使用芯片，如损坏请联系发卡行
    public static int _51 = 0x51;//	可用余额不足
    public static int _54 = 0x54;//	该卡已过期
    public static int _55 = 0x55;//	密码错
    public static int _57 = 0x57;//	不允许此卡交易
    public static int _58 = 0x58;//	发卡方不允许该卡在本终端进行此交易
    public static int _59 = 0x59;//	卡片校验错
    public static int _61 = 0x61;//	交易金额超限
    public static int _62 = 0x62;//	受限制的卡
    public static int _64 = 0x64;//	交易金额与原交易不匹配
    public static int _65 = 0x65;//	超出取款次数限制
    public static int _68 = 0x68;//	交易超时，请重试
    public static int _75 = 0x75;//	密码错误次数超限
    public static int _90 = 0x90;//	系统日切，请稍后重试
    public static int _91 = 0x91;//	发卡方状态不正常，请稍后重试
    public static int _92 = 0x92;//	发卡方线路异常，请稍后重试
    public static int _94 = 0x94;//	拒绝，重复交易，请稍后重试
    public static int _96 = 0x96;//	拒绝，交换中心异常，请稍后重试
    public static int _97 = 0x97;//	终端号未登记
    public static int _98 = 0x98;//	发卡方超时
    public static int _99 = 0x99;//	PIN格式错，请重新签到
    public static int _A0 = 0xA0;//	MAC校验错，请重新签到
    public static int _A1 = 0xA1;//	转账货币不一致
    public static int _A2 = 0xA2;//	交易成功，请向资金转入行确认
    public static int _A3 = 0xA3;//	资金到账行账号不正确
    public static int _A4 = 0xA4;//	交易成功，请向资金到账行确认
    public static int _A5 = 0xA5;//	交易成功，请向资金到账行确认
    public static int _A6 = 0xA6;//	交易成功，请向资金到账行确认
    public static int _A7 = 0xA7;//	安全处理失败

    //IC卡错误码	错误名	备注
    public static int SC_VCCERR = 0xF7CC; //电压模式错误
    public static int SC_SLOTERR = 0xF7CB; //卡通道错误
    public static int SC_PARERR = 0xF7CA; //奇偶错误
    public static int SC_PARAERR = 0xF7C9; //参数错误
    public static int SC_PROTOCALERR = 0xF7C8; //协议错误
    public static int SC_DATALENERR = 0xF7C7; //数据长度错误
    public static int SC_CARDOUT = 0xF7C6; //卡拔出
    public static int SC_NORESET = 0xF7C5; //没有初始化
    public static int SC_TIMEOUT = 0xF7C4; //卡通讯超时
    public static int SC_PPSERR = 0xF7C3; //PPS错误
    public static int SC_ATRERR = 0xF7C2; //ATR错误
    public static int SC_APDUERR = 0xF7C1; //卡通讯失败


    //非接卡错误码	错误名	备注
    public static int RET_RF_ERR_PARAM = 0xF447;//    参数错误
    public static int RET_RF_ERR_NO_OPEN = 0xF446;//    射频模块未开启
    public static int RET_RF_ERR_NOT_ACT = 0xF445;//    卡片未激活
    public static int RET_RF_ERR_MULTI_CARD = 0xF444;//    多卡冲突
    public static int RET_RF_ERR_TIMEOUT = 0xF443;//    超时无响应
    public static int RET_RF_ERR_PROTOCOL = 0xF442;//    协议错误
    public static int RET_RF_ERR_TRANSMIT = 0xF441;//    通信传输错误
    public static int RET_RF_ERR_AUTH = 0xF440;//    M1卡认证失败
    public static int RET_RF_ERR_NO_AUTH = 0xF43F;//    扇区未认证
    public static int RET_RF_ERR_VAL = 0xF43E;//    数值块数据格式有误,或DesFire卡片操作中文件大小错误
    public static int RET_RF_ERR_CARD_EXIST = 0xF43D;//    卡片仍在感应区内
    public static int RET_RF_ERR_STATUS = 0xF43C;//    卡片状态错误(如A/B卡调用M1卡接口, 或M1卡调用PiccIsoCommand接口)
    public static int RET_RF_ERR_OVERFLOW = 0xF43B;//
    public static int RET_RF_ERR_FAILED = 0xF43A;//    DesFire卡片的应答数据错误
    public static int RET_RF_ERR_COLLERR = 0xF439;//
    public static int RET_RF_ERR_FIFO = 0xF438;//    DesFire卡片操作中应用缓冲区空间不足
    public static int RET_RF_ERR_CRC = 0xF437;//
    public static int RET_RF_ERR_FRAMING = 0xF436;//
    public static int RET_RF_ERR_PARITY = 0xF435;//
    public static int RET_RF_ERR_DES_VAL = 0xF434;//    DesFire卡片应答数据DES运算结果不一致
    public static int RET_RF_ERR_NOT_ALLOWED = 0xF433;//    操作不允许, 例如当前所选文件不是记录文件时，不能执行读记录操作
    public static int RET_RF_ERR_CHIP_ABNORMAL = 0xF3E4;//    接口芯片不存在或异常
    public static int RET_RF_DET_ERR_INVALID_PARAM = 0xF37F;//
    public static int RET_RF_DET_ERR_NO_POWER = 0xF37E;//
    public static int RET_RF_DET_ERR_NO_CARD = 0xF37D;//
    public static int RET_RF_DET_ERR_COLL = 0xF37C;//
    public static int RET_RF_DET_ERR_ACT = 0xF37B;//
    public static int RET_RF_DET_ERR_PROTOCOL = 0xF37A;//
    public static int RET_RF_CMD_ERR_INVALID_PARAM = 0xF31B;//
    public static int RET_RF_CMD_ERR_NO_POWER = 0xF31A;//
    public static int RET_RF_CMD_ERR_NO_CARD = 0xF319;//
    public static int RET_RF_CMD_ERR_TX = 0xF318;//
    public static int RET_RF_CMD_ERR_PROTOCOL = 0xF317;//

    //错误名		备注
    public static int 失败 = 0xFFFF;    //打开,关闭磁条卡失败
    public static int ERR_MSR_OPENERR = 0xFD42;
    public static int ERR_MSR_NOSWIPED = 0xFD43;

    //数字键盘错误码	错误名

    public static int _0xFFFF = 0xFFFF;    //超时
    public static int _0xFFFD = 0xFFFD;    //用户取消输入(ESC键)
    public static int _0xFFFC = 0xFFFC;    //IC卡被拔除
    public static int ERR_PIN_LEN = 0xFFFB;    //密码长度模式设置不对需要大于4小于12位
    public static int KEY_ERRPAD = 0xFFFA;    //键盘(TP)模块出错
    public static int KB_UserEnter_Exit = 0xFC0B;    //用户取消输入(命令方式)
    public static int KB_UserSleep = 0xFC09;    //用户主动进入休眠
    public static int TP_TAG_ERR = 0xFC03;    //没有收到0x73这个TLV
    public static int TP_TLV_ERR = 0xFC02;    //收到的按键坐标数量不对
    public static int TP_EXG_SIZE_ERR = 0xFC01;    //收到的坐标不对,左上角与右下角坐标不符合逻辑
    public static int TP_CMDEXG_TO_ERR = 0xFC00;    //坐标交换命令等待超时
    //时钟错误码	错误名

    public static int TIME_FORMAT_ERR = 0xFC7C;//	时间格式错误
    public static int TIME_YEAR_ERR = 0xFC7B;//	年份错误
    public static int TIME_MONTH_ERR = 0xFC7A;//	月份错误
    public static int TIME_DAY_ERR = 0xFC79;//	天错误
    public static int TIME_HOUR_ERR = 0xFC78;//	时错误
    public static int TIME_MINUTE_ERR = 0xFC77;//	分错误
    public static int TIME_SECOND_ERR = 0xFC76;//	秒错误
    public static int TIME_WEEK_ERR = 0xFC75;//	周错误
    public static int TIME_SET_ERR = 0xFC74;//	设置失败
    public static int TIME_GET_ERR = 0xFC73;//	获取失败
    public static int TIME_RAMADDR_OVER = 0xFC72;//	时间内容地址超出
    public static int TIME_RAMLEN_OVER = 0xFC71;//	时间长度超出
    //密码键盘错误码	错误名

    public static int PED_RET_ERR_NO_KEY = 0xFED3;    //密钥不存在
    public static int PED_RET_ERR_KEYIDX_ERR = 0xFED2;    //密钥索引错，参数索引不在范围内
    public static int PED_RET_ERR_DERIVE_ERR = 0xFED1;    //密钥写入时，源密钥类型错或层次比目的密钥低
    public static int PED_RET_ERR_CHECK_KEY_FAIL = 0xFED0;    //密钥验证失败
    public static int PED_RET_ERR_NO_PIN_INPUT = 0xFECF;    //没输入PIN
    public static int PED_RET_ERR_INPUT_CANCEL = 0xFECE;    //用户取消输入PIN
    public static int PED_RET_ERR_WAIT_INTERVAL = 0xFECD;    //函数调用小于最小间隔时间
    public static int PED_RET_ERR_CHECK_MODE_ERR = 0xFECC;    //KCV模式错，不支持
    public static int PED_RET_ERR_NO_RIGHT_USE = 0xFECB;    //无权使用该密钥，PED当前密钥标签值和要使用的密钥标签值不相等
    public static int PED_RET_ERR_KEY_TYPE_ERR = 0xFECA;    //密钥类型错
    public static int PED_RET_ERR_EXPLEN_ERR = 0xFEC9;    //期望PIN的长度字符串错
    public static int PED_RET_ERR_DSTKEY_IDX_ERR = 0xFEC8;    //目的密钥索引错，不在范围内
    public static int PED_RET_ERR_SRCKEY_IDX_ERR = 0xFEC7;    //源密钥索引错，不在范围内或者写入密钥时，源密钥类型的值大于目的密钥类型，都会返回该密钥
    public static int PED_RET_ERR_KEY_LEN_ERR = 0xFEC6;    //密钥长度错
    public static int PED_RET_ERR_INPUT_TIMEOUT = 0xFEC5;    //输入PIN超时
    public static int PED_RET_ERR_NO_ICC = 0xFEC4;    //IC卡不存在
    public static int PED_RET_ERR_ICC_NO_INIT = 0xFEC3;    //IC卡未初始化
    public static int PED_RET_ERR_GROUP_IDX_ERR = 0xFEC2;    //DUKPT组索引号错
    public static int PED_RET_ERR_PARAM_PTR_NULL = 0xFEC1;    //指针参数非法为空
    public static int PED_RET_ERR_TAMPERED = 0xFEC0;    //PED已受攻击
    public static int PED_RET_ERROR = 0xFEBF;    //PED通用错误
    public static int PED_RET_ERR_NOMORE_BUF = 0xFEBE;    //没有空闲的缓冲
    public static int PED_RET_ERR_NEED_ADMIN = 0xFEBD;    //需要取得高级权限
    public static int PED_RET_ERR_DUKPT_OVERFLOW = 0xFEBC;    //DUKPT已经溢出
    public static int PED_RET_ERR_KCV_CHECK_FAIL = 0xFEBB;    //KCV 校验失败
    public static int PED_RET_ERR_SRCKEY_TYPE_ERR = 0xFEBA;    //写入密钥时，源密钥id的密钥类型和源密钥类型不匹配
    public static int PED_RET_ERR_UNSPT_CMD = 0xFEB9;    //命令不支持
    public static int PED_RET_ERR_COMM_ERR = 0xFEB8;    //通讯错误
    public static int PED_RET_ERR_NO_UAPUK = 0xFEB7;    //没有用户认证公钥
    public static int PED_RET_ERR_ADMIN_ERR = 0xFEB6;    //取系统敏感服务失败
    public static int PED_RET_ERR_DOWNLOAD_INACTIVE = 0xFEB5;    //PED处于下载非激活状态
    public static int PED_RET_ERR_KCV_ODD_CHECK_FAIL = 0xFEB4;    //KCV 奇校验失败
    public static int PED_RET_ERR_PED_DATA_RW_FAIL = 0xFEB3;    //读取PED数据失败
    public static int PED_RET_ERR_ICC_CMD_ERR = 0xFEB2;    //IC卡操作错误(脱机明文、密文密码验证)
    public static int PED_RET_ERR_KEY_VALUE_INVALID = 0xFEB1;    //写入的密钥全零或者，有组分相等，16/24字节密钥存在两个组分相等的情况
    public static int PED_RET_ERR_KEY_VALUE_EXIST = 0xFEB0;    //已存在相同的密钥值的密钥
    public static int PED_RET_ERR_UART_PARAM_INVALID = 0xFEAF;    //串口参数不支持
    public static int PED_RET_ERR_KEY_INDEX_NOT_SELECT_OR_NOT_MATCH = 0xFEAE;    //密钥索引没有选择或者和选择的密钥索引不相等
    public static int PED_RET_ERR_INPUT_CLEAR = 0xFEAD;    //用户按CLEAR键退出输入PIN
    public static int PED_RET_ERR_LOAD_TRK_FAIL = 0xFEAC;    //
    public static int PED_RET_ERR_TRK_VERIFY_FAIL = 0xFEAB;
    public static int PED_RET_ERR_MSR_STATUS_INVALID = 0xFEAA;    //
    public static int PED_RET_ERR_NO_FREE_FLASH = 0xFEA9;
    public static int PED_RET_ERR_DUKPT_NEED_INC_KSN = 0xFEA8;    //DUKPT KSN需要先加1
    public static int PED_RET_ERR_KCV_MODE_ERR = 0xFEA7;    //KCV MODE错误
    public static int PED_RET_ERR_DUKPT_NO_KCV = 0xFEA6;    //NO KCV
    public static int PED_RET_ERR_PIN_BYPASS_BYFUNKEY = 0xFEA5;    //按FN/ATM4键取消PIN输入
    public static int PED_RET_ERR_MAC_ERR = 0xFEA4;    //数据MAC校验错
    public static int PED_RET_ERR_CRC_ERR = 0xFEA3;    //数据CRC校验错
    public static int PED_RET_ERR_ALG_ERR = 0xFEA2;    //
    public static int PED_RET_ERR_STATE = 0xFEA1;    //PED状态错误
    public static int PED_RET_ERR_PWD = 0xFEA0;    //密码错误
    public static int PED_RET_ERR_NEWPWD = 0xFE9F;    //密码需要重设置
    public static int PED_RET_ERR_PWDOVERRUN = 0xFE9E;    //错误次数超出
    public static int PED_RET_ERR_REQ_SSA = 0xFE9D;    //需要请求敏感服务
    public static int PED_RET_ERR_KEY_KCV_TAB_NULL = 0xFE70;    //
    public static int PED_RET_ERR_PED_CFG_RW_FAIL = 0xFE6F;    //读取PED配置数据失败

    //打印错误码	错误名
    public static int ERR_PAPER = 0xFDA7;//	缺纸
    public static int ERR_OVERHEAT = 0xFDA6;//	打印头过热
    public static int ERR_PARA = 0xFDA5;//	参数错误
    public static int ERR_NO_BLACKMAKER = 0xFDA4;//	黑标检测失败
    public static int ERR_PARA_VAL = 0x9998;//	参数值错误

    public static int NET_NULL_NET = 0;//	无网络
    public static int NET_TIMEOUT = 1;//	超时
    public static int NET_DROPOUT = 2;//	丢包
    public static int NET_CHECK = 3;//	    校验错误
    public static int NET_CHECK_MAC = 4;//	mac校验错误
    public static int NET_39 = 6;//	服务器没有处理成功 就39域返回不是00
    public static int NET_PARAMETER = 7;//	报文组包错误
    public static int NET_ACTION = 8;//	action 匹配错误


}
