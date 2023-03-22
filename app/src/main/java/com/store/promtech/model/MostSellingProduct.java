package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostSellingProduct {

    @SerializedName("PrizeData")
    @Expose
    private List<ProductDatum> PrizeData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;



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

    public List<ProductDatum> getPrizeData() {
        return PrizeData;
    }

    public void setPrizeData(List<ProductDatum> prizeData) {
        PrizeData = prizeData;
    }

    public class ProductDatum {

        @SerializedName("prize_id")
        @Expose
        private String prize_id;
        @SerializedName("prize_text")
        @Expose
        private String prize_text;
        @SerializedName("font_color")
        @Expose
        private String font_color;
        @SerializedName("background_color")
        @Expose
        private String background_color;

        public String getPrize_id() {
            return prize_id;
        }

        public void setPrize_id(String prize_id) {
            this.prize_id = prize_id;
        }

        public String getPrize_text() {
            return prize_text;
        }

        public void setPrize_text(String prize_text) {
            this.prize_text = prize_text;
        }

        public String getFont_color() {
            return font_color;
        }

        public void setFont_color(String font_color) {
            this.font_color = font_color;
        }

        public String getBackground_color() {
            return background_color;
        }

        public void setBackground_color(String background_color) {
            this.background_color = background_color;
        }
    }
}
