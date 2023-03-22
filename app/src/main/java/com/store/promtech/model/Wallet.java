package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wallet {

    @SerializedName("WalletData")
    @Expose
    private List<WalletDatum> walletData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;

    public List<WalletDatum> getWalletData() {
        return walletData;
    }

    public void setWalletData(List<WalletDatum> walletData) {
        this.walletData = walletData;
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

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public class WalletDatum {


        @SerializedName("purchase_date")
        @Expose
        private String purchase_date ;

        @SerializedName("total_amount")
        @Expose
        private String total_amount ;

        @SerializedName("refund_amount")
        @Expose
        private String refund_amount ;

        public String getPurchase_date() {
            return purchase_date;
        }

        public void setPurchase_date(String purchase_date) {
            this.purchase_date = purchase_date;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }
    }

}
