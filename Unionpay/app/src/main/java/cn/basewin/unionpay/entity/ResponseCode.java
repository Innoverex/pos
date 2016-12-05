package cn.basewin.unionpay.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/6 11:13<br>
 * 描述: 应答码 <br>
 */
@Table(name = "ResponseCode")
public class ResponseCode {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "code")
    private String code = "";     //返回码
    @Column(name = "meaning")
    private String meaning = "";  //原因
    @Column(name = "type")
    private String type = "";     //类型
    @Column(name = "measures")
    private String measures;    //处理方式
    @Column(name = "log")
    private String log = "";         //提示

    public ResponseCode(String code, String meaning, String type, String measures, String log) {
        this.code = code;
        this.meaning = meaning;
        this.type = type;
        this.measures = measures;
        this.log = log;
    }

    public ResponseCode(String code, String log) {
        this.code = code;
        this.log = log;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", meaning='" + meaning + '\'' +
                ", type='" + type + '\'' +
                ", measures='" + measures + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
