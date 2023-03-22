package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banners {

    @SerializedName("BannerData")
    @Expose
    private List<BannerDatum> bannerData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("whatsapp_no")
    @Expose
    private String whatsapp_no;

    @SerializedName("delivery_charge_text")
    @Expose
    private String delivery_charge_text;
    @SerializedName("user_blocked")
    @Expose
    private String user_blocked;
    @SerializedName("tot_price")
    @Expose
    private String tot_price;
    @SerializedName("tot_qty")
    @Expose
    private String tot_qty;

    public List<BannerDatum> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerDatum> bannerData) {
        this.bannerData = bannerData;
    }

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

    public String getWhatsapp_no() {
        return whatsapp_no;
    }

    public void setWhatsapp_no(String whatsapp_no) {
        this.whatsapp_no = whatsapp_no;
    }

    public String getDelivery_charge_text() {
        return delivery_charge_text;
    }

    public void setDelivery_charge_text(String delivery_charge_text) {
        this.delivery_charge_text = delivery_charge_text;
    }

    public String getUser_blocked() {
        return user_blocked;
    }

    public void setUser_blocked(String user_blocked) {
        this.user_blocked = user_blocked;
    }

    public String getTot_price() {
        return tot_price;
    }

    public void setTot_price(String tot_price) {
        this.tot_price = tot_price;
    }

    public String getTot_qty() {
        return tot_qty;
    }

    public void setTot_qty(String tot_qty) {
        this.tot_qty = tot_qty;
    }

    public class BannerDatum {

        @SerializedName("banner_id")
        @Expose
        private String bannerId;
        @SerializedName("banner_photo")
        @Expose
        private String bannerPhoto;


        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerPhoto() {
            return bannerPhoto;
        }

        public void setBannerPhoto(String bannerPhoto) {
            this.bannerPhoto = bannerPhoto;
        }

    }
}
