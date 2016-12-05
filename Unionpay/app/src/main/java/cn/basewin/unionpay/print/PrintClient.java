package cn.basewin.unionpay.print;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.services.ServiceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/1 14:20<br>
 * version:1.0
 * 描述: 为了方便打印 <br>
 * 使用:
 * 1.得到模版常量 。设置模版具体：getgetRulesTxt(),getRulestwo_dimension.getRules_Image..
 * 2.设置内容：普通文字 ，setText(内容，模版常量) 记得加add(); 不然设置不成功。
 * add（）方法有多个 可以设置添加的出现的位置 出现位置为累加
 * add(int) 添加下标
 * addFirst() 添加到头部
 * addLast（）添加到底部
 * 3.二维码  setTwoCode(内容，模版常量)
 * 4.一维码 setOneCode(内容，模版常量)
 * 5.图片  setImage(内容，模版常量)
 * 6.所有的东西设置完后  执行 打印 print()
 * print(int) int:0 为原始打印，不带头数据和底部数据的打印
 * int:x 如果你设置了头数据和底部数据 ，x代表你设置的第几个数据 进行打印
 * 例子：如果设置为2 则会去 头List().get(1) +原始数据+底List().get(1)
 * 7.打印数据组成
 * | ——————————————————————————|
 * |                           |  List<头部数据包>
 * |          头数据            |
 * |————————————————————————————
 * |                           |
 * |                           |
 * |         原始数据           |
 * |                           |
 * |                           |
 * |————————————————————————————
 * |                           |
 * |         底部数据            |   List<底部数据包>
 * |———————————————————————————|
 * <p/>
 * 特殊功能：
 * 1.设置头数据和 底部数据
 * 头数据 setTtitleXXXXXX(内容，模版);
 * 底数据 setTbottomXXXXXX(内容，模版);
 * 这一联数据设置玩后 执行  .TSave();保存进头部和底部的集合
 * 2.自动打印
 * printNext(); 只要不停调用这个 他会更具头部和底部数据的集合大小不停进行下一联打印
 * printNextForTask(); 这个打印需要提前设置好任务列表设置setNextTask(int[]) 设置好后可根据这个数组的下标进行打印。
 * 3.设置打印数据包
 * 可能您有个一个数据模版是经常用的而且还是固定的请执行
 * 1.new 一个这个打印类
 * 2.设置原始数据记得.add();
 * 3.执行getPrintData（）；拿到“数据包”把这个数据包作为一个静态数据集
 * <p/>
 * 4.new 一个打印类
 * 5.addTPrintDataBottom(数据包).TSave()  //添加底部数据  如果您还想加数据可以在setTbottomXXXXXX(内容，模版); 无论你怎么做最后加个TSave 保持这一联数据就行。
 * addTPrintDataTitle(数据包).TSave()   //添加头部数据
 * addPrintData(数据包);这个不执行add操作；
 * 通过这样的操作能把固定的数据集设置到打印类中
 */
public class PrintClient {
    private static final String TAG = PrintClient.class.getName();

    private int type_print = type_comm;//普通打印 通过 游标打印
    private static final int type_comm = 0;//普通打印 通过 游标打印
    private static final int type_next = 1;//next打印 通过next方法进行的打印
    private static final int type_task = 2;//task打印 通过task方法进行的打印
    /**
     * 内部打印监听 作用域数据监听
     */
    private OnPrinterListener _Listen;

    //--------------------基本的数据 ：原数据：不能改变的数据-----------------------------------
    private LinkedList<JSONObject> datas = new LinkedList<>();
    //图片数据
    private List<Bitmap> bitmaps = new ArrayList<>();

    //--------------------中转数据----------------------------------------------------------
    private JSONObject data;
    private Bitmap bit;

    //--------------------临时数据 ：也就是打印的时候需要选择 性的 打印 数据开头.T代表临时的意思--------
    //title 数据
    private List<LinkedList<JSONObject>> TtitleDataAll = new ArrayList<>();
    private List<List<Bitmap>> TtitleImageAll = new ArrayList<>();

    private LinkedList<JSONObject> TtitleDatas = new LinkedList<>();
    private List<Bitmap> TtitleImageDatas = new ArrayList<>();
    //底部  数据
    private List<LinkedList<JSONObject>> TbottomDataAll = new ArrayList<>();
    private List<List<Bitmap>> TbottomImageAll = new ArrayList<>();

    private LinkedList<JSONObject> TbottomDatas = new LinkedList<>();
    private List<Bitmap> TbottomImageDatas = new ArrayList<>();

    public PrintClient() {
        super();
        setPrintListen(null);
    }

    /**
     * 设置打印监听
     *
     * @param listen
     */
    public void setPrintListen(final OnPrinterListener listen) {
        _Listen = new OnPrinterListener() {
            @Override
            public void onError(int i, String s) {
                if (listen != null) {
                    listen.onError(i, s);
                }
            }

            @Override
            public void onFinish() {
                switch (type_print) {
                    case type_comm:
                        break;
                    case type_next:
                        cursorAuto++;
                        break;
                    case type_task:
                        cursorTask++;
                        break;
                }
                if (listen != null) {
                    listen.onFinish();
                }
            }

            @Override
            public void onStart() {
                if (listen != null) {
                    listen.onStart();
                }
            }
        };
    }
    //-------------------------------------打印游标------------------------------------------------
    /**
     * 自动打印的游标
     */
    private int cursorAuto = 0;
    /**
     * 打印的当前游标
     */
    private int cursorPrint = 0;
    /**
     * 任务盏方式打印的  序列
     */
    private int[] task = null;
    /**
     * task 数组的游标
     */
    private int cursorTask = 0;

    //-------------------------------------打印的一个数据包-------------------------------------------

    /**
     * 将打印的数据包（包括图片）取出 不带头和底部的数据
     *
     * @return
     */
    public PrintData getPrintData() {
        PrintData mPrintData = new PrintData();
        mPrintData.setpBitmaps(bitmaps);
        mPrintData.setpDatas(datas);
        return mPrintData;
    }

    /**
     * 定义一个打印的数据包  json 和图片的集合体
     */
    public class PrintData {
        private LinkedList<JSONObject> pDatas = new LinkedList<>();
        private List<Bitmap> pBitmaps = new ArrayList<>();

        public LinkedList<JSONObject> getpDatas() {
            return pDatas;
        }

        public void setpDatas(LinkedList<JSONObject> pDatas) {
            this.pDatas = pDatas;
        }

        public List<Bitmap> getpBitmaps() {
            return pBitmaps;
        }

        public void setpBitmaps(List<Bitmap> pBitmaps) {
            this.pBitmaps = pBitmaps;
        }
    }

    //-------------------------------------确认-添加到容器---------------------------------------------

    /**
     * 保存这一联数据，T开口的数据不用每一步都进行add 操作 只需要这一联数据全设置完后 进行TSave操作就行了
     */
    public void TSave() {
        TSaveTitle();
        TSaveBottom();
    }

    public void TSaveTitle() {
        //这一组 的title 数据保存
        TtitleDataAll.add(TtitleDatas);
        TtitleImageAll.add(TtitleImageDatas);
        TtitleDatas = new LinkedList<>();
        TtitleImageDatas = new ArrayList<>();
    }

    public void TSaveBottom() {
        //bottom数据保存
        TbottomDataAll.add(TbottomDatas);
        TbottomImageAll.add(TbottomImageDatas);
        TbottomDatas = new LinkedList<>();
        TbottomImageDatas = new ArrayList<>();
    }

    /**
     * 基本数据的添加 确认操作
     * 1.有一个设置的数据 就添加到数据集合里面
     * 2.判断有没有图片
     * 3.查看datad 类型是不是图片 是的话就添加图片 不是清空图片
     *
     * @return
     */
    public PrintClient add() {
        if (data == null) {
            return this;
        }
        datas.add(data);
        data = null;
        if (bit != null) {
            try {
                if (!datas.getLast().get("content-type").equals("jpg")) {
                    bit = null;
                    return this;
                }
            } catch (JSONException e) {
                bit = null;
                e.printStackTrace();
                return this;
            }
            bitmaps.add(bit);
            bit = null;
        }
        return this;
    }

    public PrintClient add(boolean b) {
        if (b) {
            add();
        } else {
            data = null;
            bit = null;
        }
        return this;
    }

    //不支持图片
    public PrintClient add(int location) {
        if (data == null) {
            return this;
        }
        datas.add(location, data);
        data = null;
        return this;
    }

    //不支持图片
    public PrintClient addFirst() {
        if (data == null) {
            return this;
        }
        datas.addFirst(data);
        data = null;
        return this;
    }

    //不支持图片
    public PrintClient addLast() {
        if (data == null) {
            return this;
        }
        datas.addLast(data);
        data = null;
        return this;
    }

    //------------------------------------设置数据---------------------------------------------------
    //设置打印的数据 原数据
    public LinkedList<JSONObject> getDatas() {
        return datas;
    }

    public void setDatas(LinkedList<JSONObject> datas) {
        this.datas = datas;
    }

    //往末尾 添加一段数据
    public void putData(LinkedList<JSONObject> _data) {
        datas.addAll(_data);
    }

    //往自定义位置  添加一段数据
    public void putData(int location, LinkedList<JSONObject> _data) {
        datas.addAll(location, _data);
    }

    //往头部位置  添加一段数据
    public void putDataTitle(LinkedList<JSONObject> _data) {
        datas.addAll(0, _data);
    }

    //-------设置一段数据
    public PrintClient addPrintData(PrintData pd) {
        if (judgePrintData(pd)) {
            datas.addAll(pd.getpDatas());
        }
        if (judgePrintImage(pd)) {
            bitmaps.addAll(pd.getpBitmaps());
        }
        return this;
    }

    /**
     * 将 打印的数据集  设置到头部
     *
     * @param pd
     * @return
     */
    public PrintClient addTPrintDataTitle(PrintData pd) {
        if (judgePrintData(pd)) {
            TtitleDatas.addAll(pd.getpDatas());
        }
        if (judgePrintImage(pd)) {
            TtitleImageDatas.addAll(pd.getpBitmaps());
        }
        return this;
    }

    /**
     * 将 打印的数据集  设置到底部
     *
     * @param pd 打印的数据集
     * @return
     */
    public PrintClient addTPrintDataBottom(PrintData pd) {
        if (judgePrintData(pd)) {
            TbottomDatas.addAll(pd.getpDatas());
        }
        if (judgePrintImage(pd)) {
            TbottomImageDatas.addAll(pd.getpBitmaps());
        }
        return this;
    }

    /**
     * 判断数据包是否有数据
     *
     * @param pd
     * @return
     */
    private boolean judgePrintData(PrintData pd) {
        if (pd != null && pd.getpDatas() != null && pd.getpDatas().size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断图片数据是否有
     *
     * @param pd
     * @return
     */
    private boolean judgePrintImage(PrintData pd) {
        if (pd != null && pd.getpBitmaps() != null && pd.getpBitmaps().size() > 0) {
            return true;
        }
        return false;
    }

    //---------设置图片=---放心大胆的把图片 路径 或者资源文件名丢进去 然后不管了
    public PrintClient setTTitleImage(String path, Map<String, String> m) throws JSONException {
        setImage(path, m);
        if (bit != null) {
            TtitleImageDatas.add(bit);
            TtitleDatas.add(data);
            data = null;
            bit = null;
        }
        return this;
    }

    /**
     * 往 底部区域设置图片
     *
     * @param path
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTBootmImage(String path, Map<String, String> m) throws JSONException {
        setImage(path, m);
        if (bit != null) {
            TbottomImageDatas.add(bit);
            TtitleDatas.add(data);
            data = null;
            bit = null;
        }
        return this;
    }

    /**
     * 往原始区域 设置图片数据
     *
     * @param path
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setImage(String path, Map<String, String> m) throws JSONException {
        //1先通过 路径找图片
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            try {
                //2通过 资源文件找图片
                InputStream open = AppContext.getInstance().getAssets().open(path);
                if (open == null) {
                    return this;
                } else {
                    bitmap = BitmapFactory.decodeStream(open);
                    if (bitmap == null) {
                        Log.e(TAG, "设置的图片为null");
                        open.close();
                        return this;
                    }
                    open.close();
                    return setImage(bitmap, m);
                }
            } catch (IOException e) {
                TLog.e(TAG, "没有找到图片！path:" + path);
                e.printStackTrace();
                return this;
            }
        } else {
            return setImage(bitmap, m);
        }
    }

    /**
     * 设置图片站位
     *
     * @param b
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setImage(Bitmap b, Map<String, String> m) throws JSONException {
        bit = b;
        return setImageBit(m);
    }

    public PrintClient setImageBit(Map<String, String> m) throws JSONException {
        return setImageBit(m.get("position"));
    }

    public PrintClient setImageBit(String position) throws JSONException {
        position = TextUtils.isEmpty(position) ? "center" : position;
        JSONObject j = new JSONObject();
        j.put("content-type", "jpg");
        j.put("position", position);
        data = j;
        return this;
    }

    /**
     * 头部区域设置 二维码
     *
     * @param msg
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTtitleTwoCode(String msg, Map<String, String> m) throws JSONException {
        setTwoCode(msg, m);
        setTTitle();
        return this;
    }

    /**
     * 底部区域设置 二维码
     *
     * @param msg
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTbottomTwoCode(String msg, Map<String, String> m) throws JSONException {
        setTwoCode(msg, m);
        setTBottom();
        return this;
    }

    /**
     * 原始区域设置 二维码
     *
     * @param content
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTwoCode(String content, Map<String, String> m) throws JSONException {
        if (TextUtils.isEmpty(content)) {
            return this;
        }
        String size = m.get("size");
        String position = m.get("position");
        String height = m.get("height");
        return setTwoCode(content, size, position, height);
    }

    /**
     * 二维码 json
     *
     * @param content
     * @param size
     * @param position
     * @param height
     * @return
     * @throws JSONException
     */
    public PrintClient setTwoCode(String content, String size, String position, String height) throws JSONException {
        size = TextUtils.isEmpty(size) ? "3" : size;
        position = TextUtils.isEmpty(position) ? "center" : position;
        height = TextUtils.isEmpty(height) ? "1" : height;
        JSONObject j = new JSONObject();
        j.put("content-type", "two-dimension");
        j.put("content", content);
        j.put("size", size);
        j.put("position", position);
        j.put("height", height);
        data = j;
        return this;
    }

    /**
     * 头部区域 设置 一维码
     *
     * @param content
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTtitleOneCode(String content, Map<String, String> m) throws JSONException {
        setOneCode(content, m);
        setTTitle();
        return this;
    }

    /**
     * 底部区域 设置 一维码
     *
     * @param content
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTBottomOneCode(String content, Map<String, String> m) throws JSONException {
        setOneCode(content, m);
        setTBottom();
        return this;
    }

    /**
     * 原始区 设置 一维码
     *
     * @param content
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setOneCode(String content, Map<String, String> m) throws JSONException {
        if (TextUtils.isEmpty(content)) {
            return this;
        }
        String size = m.get("size");
        String position = m.get("position");
        String height = m.get("height");
        return setOneCode(content, size, position, height);
    }

    /**
     * sdk 需要的一维码的json
     *
     * @param content
     * @param size
     * @param position
     * @param height
     * @return
     * @throws JSONException
     */
    public PrintClient setOneCode(String content, String size, String position, String height) throws JSONException {
        size = TextUtils.isEmpty(size) ? "3" : size;
        position = TextUtils.isEmpty(position) ? "center" : position;
        height = TextUtils.isEmpty(height) ? "2" : height;
        JSONObject j = new JSONObject();
        j.put("content-type", "one-dimension");
        j.put("content", content);
        j.put("size", size);
        j.put("position", position);
        j.put("height", height);
        data = j;
        return this;
    }

    /**
     * 头部区域 设置 文字
     *
     * @param msg
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTtitleText(String msg, Map<String, String> m) throws JSONException {
        setText(msg, m);
        setTTitle();
        return this;
    }

    /**
     * 底部区域 设置 文字
     *
     * @param msg
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setTbottomText(String msg, Map<String, String> m) throws JSONException {
        setText(msg, m);
        setTBottom();
        return this;
    }

    /**
     * 原始区域 设置 文字
     *
     * @param msg
     * @param m
     * @return
     * @throws JSONException
     */
    public PrintClient setText(String msg, Map<String, String> m) throws JSONException {
        String s = m.get("content-type");
        String size = m.get("size");
        String position = m.get("position");
        String offset = m.get("offset");
        String bold = m.get("bold");
        String italic = m.get("italic");
        String height = m.get("height");
        return setText(msg, size, position, offset, bold, italic, height);
    }

    /**
     * sdk 需要的 text json
     *
     * @param text
     * @param size
     * @param position
     * @param offset
     * @param bold
     * @param italic
     * @param height
     * @return
     * @throws JSONException
     */
    public PrintClient setText(String text, String size,
                               String position, String offset, String bold, String italic, String height) throws JSONException {
        text = TextUtils.isEmpty(text) ? " " : text;// 打印数据
        size = TextUtils.isEmpty(size) ? "1" : size;// 位置 大小 1 2 3
        position = TextUtils.isEmpty(position) ? "left" : position;// 位置 center，left，right
        bold = TextUtils.isEmpty(bold) ? "0" : bold;// 加粗 0 正常 1加粗
        italic = TextUtils.isEmpty(italic) ? "0" : italic;// 加斜体 0 正常 1加粗
        height = TextUtils.isEmpty(height) ? "0" : height;// 高度 1 2 3
        offset = TextUtils.isEmpty(offset) ? "0" : offset;

        JSONObject json1 = new JSONObject();
        json1.put("content-type", "txt");//内容类型, 可选字段有”txt”,“jpg”,”one-dimension”(一维码),“two-dimension”(二维码)
        json1.put("content", text);
        json1.put("size", size);
        json1.put("position", position);//对齐方式, 可选字段有”left”, “center”, “right”(选填)默认为left
        json1.put("offset", offset);//偏移量,暂时无效
        json1.put("bold", bold);//“1”表示字体加粗, “0”表示不加粗
        json1.put("italic", italic);//“1”表示斜体, “0”表示正常
        json1.put("height", height);//一维码高度, 可选1-3
        data = json1;
        return this;
    }

    /**
     * 找到刚刚设置的 数据把这个数据放到头部数据集中
     */
    private void setTTitle() {
        if (data == null) {
            return;
        }
        TtitleDatas.add(data);
        data = null;
    }

    private void setTBottom() {
        if (data == null) {
            return;
        }
        TbottomDatas.add(data);
        data = null;
    }

    //--------------------------------------模版设置-------------------------------------------------
    //得到一个text的字体类型模版                               大小          左右中             偏移          粗体          斜体      高度
    public static Map<String, String> getRulesTxt(String size, String position, String offset, String bold, String italic, String height) {
        Map<String, String> map = new HashMap<>();
        map.put("content-type", "txt");
        map.put("size", size);
        map.put("position", position);//对齐方式, 可选字段有”left”, “center”, “right”(选填)默认为left
        map.put("offset", "0");//偏移量,暂时无效
        map.put("bold", bold);//“1”表示字体加粗, “0”表示不加粗
        map.put("italic", italic);//“1”表示斜体, “0”表示正常
        map.put("height", "-1");//一维码高度, 可选1-3
        return map;
    }

    //得到二维码模版
    public static Map<String, String> getRulestwo_dimension(String size, String position, String height) {
        Map<String, String> map = new HashMap<>();
        map.put("content-type", "two-dimension");
        map.put("size", size);   //二维码 1--8
        map.put("position", position);//left”, “center”, “right”
        map.put("height", height);
        return map;
    }

    //得到一维码模版
    public static Map<String, String> getRulesone_dimension(String size, String position, String height) {
        Map<String, String> map = new HashMap<>();
        map.put("content-type", "one-dimension");
        map.put("size", size);//二维码 1--8
        map.put("position", position);////left”, “center”, “right”
        map.put("height", height);//一维码高度, 可选1-3
        return map;
    }

    //得到图片模版
    public static Map<String, String> getRules_Image(String position) {
        Map<String, String> map = new HashMap<>();
        map.put("content-type", "jpg");
        map.put("position", position);//left”, “center”, “right”
        return map;
    }

    //---------------------------------------------------------------------------------------------

    //---------------------------------打印 开始----------------------------------------------------
    private String _font = AppConfig.PRINT_FONT;//字体
    private int _LineSpace = AppConfig.PRINT_LINESPACE;//行间距
    private int _Gray = AppConfig.PRINT_GRAY;//灰度
    private int _BottomFeedLine = AppConfig.PRINT_BOTTOMFEEDLINE;//底部空行

    public void set_font(String _font) {
        this._font = _font;
    }

    public void set_LineSpace(int _LineSpace) {
        this._LineSpace = _LineSpace;
    }

    public void set_Gray(int _Gray) {
        this._Gray = _Gray;
    }

    public void set_BottomFeedLine(int _BottomFeedLine) {
        this._BottomFeedLine = _BottomFeedLine;
    }

    //**********************************打印--------------------------------------------------------

    /**
     * 封装了下打印的 基本类型  然后进行打印
     *
     * @param json
     * @param bt
     * @param _listen
     * @throws Exception
     */
    public void print(String json, Bitmap[] bt, OnPrinterListener _listen) throws Exception {
        if (!TextUtils.isEmpty(_font)) {
            ServiceManager.getInstence().getPrinter().setPrintFont(_font);
        }
        if (_LineSpace > 0) {
            ServiceManager.getInstence().getPrinter().setLineSpace(_LineSpace);
        }
        if (_Gray > 0) {
            ServiceManager.getInstence().getPrinter().setPrintGray(_Gray);
        }
        if (_BottomFeedLine > 0) {
            ServiceManager.getInstence().getPrinter().printBottomFeedLine(_BottomFeedLine);
        }
        TLog.e(TAG, json);
        if (bt != null) {
            TLog.e(TAG, "Bitmap size :" + bt.length);
        }
        ServiceManager.getInstence().getPrinter().print(json, bt, _listen);
    }

    /**
     * 打印原始区域数据
     *
     * @throws Exception
     */
    public void print() throws Exception {
        print(0);
    }

    /**
     * 打印 选择的下标的数据
     *
     * @param positions
     * @throws Exception
     */
    public void print(int positions) throws Exception {
        print(positions, type_comm);
    }

    /**
     * @param positions 打印的游标
     * @param type      打印的类型 如0，普通打印，1自动下一打印 2.任务盏 打印
     * @throws Exception
     */
    public void print(int positions, int type) throws Exception {
        type_print = type;
        int p = positions - 1;
        JSONArray j = new JSONArray();
        List<Bitmap> bip = new ArrayList<>();
        Bitmap[] b = null;
        ;
        //-----------组合 拼接--------
        //----------title
        if (p >= 0) {
            if (TtitleDataAll != null && TtitleDataAll.size() > 0 && TtitleDataAll.get(p) != null && TtitleDataAll.get(p).size() > 0) {
                LinkedList<JSONObject> tj = TtitleDataAll.get(p);
                for (int i = 0; i < tj.size(); i++) {
                    j.put(tj.get(i));
                }

            }
            if (TtitleImageAll != null && TtitleImageAll.size() > 0) {
                List<Bitmap> bs = TtitleImageAll.get(p);
                if (bs != null && bs.size() > 0) {
                    for (int i = 0; i < bs.size(); i++) {
                        Bitmap bitmap = bs.get(i);
                        bip.add(bitmap);
                    }
                }
            }
        }
        //------基本数据
        for (int i = 0; i < datas.size(); i++) {
            j.put(datas.get(i));
        }
        if (bitmaps != null && bitmaps.size() > 0) {
            for (int i = 0; i < bitmaps.size(); i++) {
                bip.add(bitmaps.get(i));
            }
        }
        //------底部数据
        if (p >= 0) {
            if (TbottomDataAll !=
                    null && TbottomDataAll.size() > 0 && TbottomDataAll.get(p) != null && TbottomDataAll.get(p).size() > 0) {
                LinkedList<JSONObject> tj = TbottomDataAll.get(p);
                for (int i = 0; i < tj.size(); i++) {
                    j.put(tj.get(i));
                }

            }
            if (TbottomImageAll != null && TbottomImageAll.size() > 0) {
                List<Bitmap> bs = TbottomImageAll.get(p);
                if (bs != null && bs.size() > 0) {
                    for (int i = 0; i < bs.size(); i++) {
                        Bitmap bitmap = bs.get(i);
                        bip.add(bitmap);
                    }
                }
            }
        }
        //-------------------
        if (bip != null && bip.size() > 0) {
            b = new Bitmap[bip.size()];
            for (int i = 0; i < bip.size(); i++) {
                b[i] = bip.get(i);
            }
        }

        JSONObject printJson = new JSONObject();
        printJson.put("spos", j);

        if (_Listen == null) {
            Log.e(TAG, "打印监听未设置！");
        }
        print(printJson.toString(), b, _Listen);
    }

    /**
     * 1.验证下 下一步打印是否能通过
     * 2.拿出下一次任务
     * 3.打印
     *
     * @return
     * @throws Exception
     */
    public boolean printNextForTask() throws Exception {
        if (!getIsprintNextForTask()) {
            return false;
        }
        int i1 = cursorTask + 1;
        int i = task[i1];
        print(i, type_task);
        return true;
    }

    /**
     * 1.判断是否设置了任务
     * 2.如果在当前任务的下标+1的情况下是不是大于了总的任务量
     * 3.当前任务task的 打印游标是不是大于 总打印数据的数量
     *
     * @return
     */
    public boolean getIsprintNextForTask() {
        if (task == null || task.length <= 0) {
            return false;
        }
        if ((cursorTask + 1) > task.length) {
            return false;
        }
        int c = task[cursorTask + 1];
        int s = TtitleDataAll.size() > TbottomDataAll.size() ? TtitleDataAll.size() : TbottomDataAll.size();
        if (c > s) {
            return false;
        }
        return true;
    }


    /**
     * 默认下一步打印 不通过 task 进行
     *
     * @return false 为不打印   true：即将打印
     * @throws Exception
     */
    public boolean printNext() throws Exception {
        if (!getIsPrintNext()) {
            return false;
        }
        int i = cursorAuto + 1;
        print(i, type_next);
        return true;
    }

    /**
     * 下一连打印是否能进行
     * 1.判断是否有多个数据 要打印
     * 2.在当前打印任务的下一个任务 是不是 超出了 打印总数量的范围
     *
     * @return 判断下一个打印是否能进行 如果没数据返回false 证明不能进行
     */
    public boolean getIsPrintNext() {
        if (TtitleDataAll.size() <= 0 && TbottomDataAll.size() <= 0) {
            return false;
        }
        int i = TtitleDataAll.size() > TbottomDataAll.size() ? TtitleDataAll.size() : TbottomDataAll.size();
        if (cursorAuto + 1 > i) {
            return false;
        }
        return true;
    }

    /**
     * 设置通过任务列表打印的 任务列表
     * 例子：int[]{0,2,1} 就会依次打印 原始数据 ----》 title 和bottom 0下标的数据----》原始数据
     *
     * @param nextTask
     */
    public void setNextTask(int[] nextTask) {
        this.task = nextTask;
    }
}
