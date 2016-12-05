package cn.basewin.unionpay.utils;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/15 15:23<br>
 * 描述：
 */
public class FormatUtil {

    /**
     * 右对齐补0
     * 字符串长度需要要小于等于目标长度，否则会抛出IndexOutOfBoundsException
     *
     * @param str
     * @param length 长度
     * @return
     */
    public static String alignRigthFillZero(String str, int length) {
        if (str.length() > length) {
            throw new IndexOutOfBoundsException("长度错误");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = str.length(); i < length; i++) {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 左对齐补空格
     * 字符串长度需要要小于等于目标长度，否则会抛出IndexOutOfBoundsException
     *
     * @param str
     * @param length 长度
     * @return
     */
    public static String alignLeftFillSpace(String str, int length) {
        if (str.length() > length) {
            throw new IndexOutOfBoundsException("长度错误");
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = str.length(); i < length; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
