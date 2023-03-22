package com.store.promtech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {
    @SerializedName("Ack")
    private Long mAck;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("OfferData")
    private List<OfferDatum> mOfferData;

    public Long getAck() {
        return mAck;
    }

    public void setAck(Long ack) {
        mAck = ack;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<OfferDatum> getOfferData() {
        return mOfferData;
    }

    public void setOfferData(List<OfferDatum> offerData) {
        mOfferData = offerData;
    }


}
