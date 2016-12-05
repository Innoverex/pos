package cn.basewin.unionpay.utils;

public class CommParam {
    private static String ConditionCode = null; // 交易特征码
    private static String StoreFileName = null; // 保存文件路径和名字

    private static String JbgFileSavePath = null; // jbig 数据压缩后的保存的路径
    private static String JbgFileName = null; // jbig 数据压缩后保存的文件名

    private static String OriginalFileSavePath = null; // 原始文件保存的路径
    private static String OriginalFileName = null; // 原始文件保存的文件名

    // 打印文件保存路劲
    private static String PrintbmpFileSavePath = null; // 打印文件保存的路径
    private static String PrintbmpFileName = null; // 打印文件保存的文件名

    private static boolean isSign = false; // 是否签字标志

    private static int overtime = 0;

    private static int ViewWidth = 0;
    private static int ViewHeight = 0;

    public static int RecWidth = 0;
    public static int RecHeight = 0;

    private static int blackpixel = 0; // 签字黑色总象素占整个整体象素比例 *100
    private static int refBlackPixelPer = 0; // 参考值 签字黑色总象素占整体象素比例

    public void setConditionCode(String ConditionCode) {
        this.ConditionCode = ConditionCode;
    }

    public String getConditionCode() {
        return ConditionCode;
    }

    public void setStroFileName(String StoreFileName) {
        this.StoreFileName = StoreFileName;
    }

    public String getStroFileName() {
        return StoreFileName;
    }

    // private static String JbgFileSavePath = null; //jbig 数据压缩后的保存的路径
    // private static String JbgFileName = null; //jbig 数据压缩后保存的文件名

    public void setJbgFileSavePath(String JbgFileSavePath) {
        this.JbgFileSavePath = JbgFileSavePath;
    }

    public String getJbgFileSavePath() {
        return JbgFileSavePath;
    }

    public void setJbgFileName(String JbgFileName) {
        this.JbgFileName = JbgFileName;
    }

    public String getJbgFileName() {
        return JbgFileName;
    }

    public void setStroOriginalFileName(String OriginalFileName) {
        this.OriginalFileName = OriginalFileName;
    }

    public String getStroOriginalFileName() {
        return OriginalFileName;
    }

    public void setOrignalFileSavePath(String OriginalFileSavePath) {
        this.OriginalFileSavePath = OriginalFileSavePath;
    }

    public String getOrignalFileSavePath() {
        return OriginalFileSavePath;
    }

    public void setPrintbmpFileSavePath(String PrintbmpFileSavePath) {
        this.PrintbmpFileSavePath = PrintbmpFileSavePath;
    }

    public String getPrintbmpFileSavePath() {
        return PrintbmpFileSavePath;
    }

    public void setPrintbmpFileName(String PrintbmpFileName) {
        this.PrintbmpFileName = PrintbmpFileName;
    }

    public String getPrintbmpFileName() {
        return PrintbmpFileName;
    }

    public void setTimeOver(int overtime) {
        this.overtime = overtime;
    }

    public int getTimeOver() {
        return overtime;
    }

    public void setViewWidth(int ViewWidth) {
        this.ViewWidth = ViewWidth;
    }

    public int getViewWidth() {
        return ViewWidth;
    }

    public void setViewHeight(int ViewHeight) {
        this.ViewHeight = ViewHeight;
    }

    public int getViewHeight() {
        return ViewHeight;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public boolean getIsSign() {
        return isSign;
    }

    public void setBlackPixel(int blackpixel) {
        this.blackpixel = blackpixel;
    }

    public int getBlackPixel() {
        return blackpixel;
    }

    public void setRefBlackPixel(int refBlackPixelPer) {
        this.refBlackPixelPer = refBlackPixelPer;
    }

    public int getRefBlackPixelPer() {
        return refBlackPixelPer;
    }
}
