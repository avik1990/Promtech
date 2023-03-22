package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {

    @SerializedName("BrandData")
    @Expose
    private List<BrandDatum> brandData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<BrandDatum> getBrandData() {
        return brandData;
    }

    public void setBrandData(List<BrandDatum> brandData) {
        this.brandData = brandData;
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
    public class BrandDatum {

        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("brand_photo")
        @Expose
        private String brandPhoto;

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandPhoto() {
            return brandPhoto;
        }

        public void setBrandPhoto(String brandPhoto) {
            this.brandPhoto = brandPhoto;
        }

    }
}
