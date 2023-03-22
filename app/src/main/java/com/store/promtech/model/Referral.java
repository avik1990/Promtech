package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Referral {
    @SerializedName("total_earn")
    @Expose
    private String total_earn;
    @SerializedName("ref_code")
    @Expose
    private String ref_code;
    @SerializedName("wallet_text")
    @Expose
    private String wallet_text;

    @SerializedName("EarnData")
    @Expose
    private List<RefDatum> refData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    public String getTotal_earn() {
        return total_earn;
    }

    public void setTotal_earn(String total_earn) {
        this.total_earn = total_earn;
    }

    public List<RefDatum> getRefData() {
        return refData;
    }

    public void setRefData(List<RefDatum> refData) {
        this.refData = refData;
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

    public String getWallet_text() {
        return wallet_text;
    }

    public void setWallet_text(String wallet_text) {
        this.wallet_text = wallet_text;
    }

    public class RefDatum {

        @SerializedName("user_name")
        @Expose
        private String user_name;
        @SerializedName("earn")
        @Expose
        private String earn;


        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getEarn() {
            return earn;
        }

        public void setEarn(String earn) {
            this.earn = earn;
        }
    }
}
