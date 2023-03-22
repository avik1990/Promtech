package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdvantageList {

    @SerializedName("AdvantageData")
    @Expose
    private List<AdvantageData> PrizeData = null;
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

    public List<AdvantageData> getPrizeData() {
        return PrizeData;
    }

    public void setPrizeData(List<AdvantageData> prizeData) {
        PrizeData = prizeData;
    }

    public class AdvantageData {

        @SerializedName("advantage_id")
        @Expose
        private int advantage_id;
        @SerializedName("advantage_photo")
        @Expose
        private String advantage_photo;
        @SerializedName("advantage_text")
        @Expose
        private String advantage_text;

        public int getAdvantage_id() {
            return advantage_id;
        }

        public void setAdvantage_id(int advantage_id) {
            this.advantage_id = advantage_id;
        }

        public String getAdvantage_photo() {
            return advantage_photo;
        }

        public void setAdvantage_photo(String advantage_photo) {
            this.advantage_photo = advantage_photo;
        }

        public String getAdvantage_text() {
            return advantage_text;
        }

        public void setAdvantage_text(String advantage_text) {
            this.advantage_text = advantage_text;
        }
    }
}
