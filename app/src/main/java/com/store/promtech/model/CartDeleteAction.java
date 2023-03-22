package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDeleteAction {

    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg3")
    @Expose
    private String msg3;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("msg2")
    @Expose
    private String msg2;

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }
}
