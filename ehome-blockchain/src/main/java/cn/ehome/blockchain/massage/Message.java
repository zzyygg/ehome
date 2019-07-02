package cn.ehome.blockchain.massage;

import java.io.Serializable;

/**
 * @author:Jun
 * @time:2019/3/26
 */
public class Message implements Serializable {

    private int type;
    private String data;

    public Message(int type) {
        this.type = type;
    }

    public Message(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
