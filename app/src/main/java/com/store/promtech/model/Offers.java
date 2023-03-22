package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offers {
    @SerializedName("OfferData")
    @Expose
    private List<Offers.ProductDatum> OfferData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;


    public List<ProductDatum> getOfferData() {
        return OfferData;
    }

    public void setOfferData(List<ProductDatum> offerData) {
        OfferData = offerData;
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


    public class ProductDatum {

        @SerializedName("offer_id")
        @Expose
        private String offer_id;
        @SerializedName("offer_photo")
        @Expose
        private String offer_photo;


        public String getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(String offer_id) {
            this.offer_id = offer_id;
        }

        public String getOffer_photo() {
            return offer_photo;
        }

        public void setOffer_photo(String offer_photo) {
            this.offer_photo = offer_photo;
        }
    }
}
