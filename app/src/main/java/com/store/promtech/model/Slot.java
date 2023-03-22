package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Slot {

    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("pickupDateData")
    @Expose
    private String pickupDateData;
    @SerializedName("PickupTimeData")
    @Expose
    private List<String> pickupTimeData = null;

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPickupDateData() {
        return pickupDateData;
    }

    public void setPickupDateData(String pickupDateData) {
        this.pickupDateData = pickupDateData;
    }

    public List<String> getPickupTimeData() {
        return pickupTimeData;
    }

    public void setPickupTimeData(List<String> pickupTimeData) {
        this.pickupTimeData = pickupTimeData;
    }

}
