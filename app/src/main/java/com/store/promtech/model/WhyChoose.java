package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WhyChoose {


    @SerializedName("ChooseData")
    @Expose
    private List<ChooseDatum> chooseData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<ChooseDatum> getChooseData() {
        return chooseData;
    }

    public void setChooseData(List<ChooseDatum> chooseData) {
        this.chooseData = chooseData;
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


    public class ChooseDatum {

        @SerializedName("choose_id")
        @Expose
        private String chooseId;
        @SerializedName("choose_photo")
        @Expose
        private String choosePhoto;
        @SerializedName("choose_title")
        @Expose
        private String chooseTitle;
        @SerializedName("choose_text")
        @Expose
        private String chooseText;

        public String getChooseId() {
            return chooseId;
        }

        public void setChooseId(String chooseId) {
            this.chooseId = chooseId;
        }

        public String getChoosePhoto() {
            return choosePhoto;
        }

        public void setChoosePhoto(String choosePhoto) {
            this.choosePhoto = choosePhoto;
        }

        public String getChooseTitle() {
            return chooseTitle;
        }

        public void setChooseTitle(String chooseTitle) {
            this.chooseTitle = chooseTitle;
        }

        public String getChooseText() {
            return chooseText;
        }

        public void setChooseText(String chooseText) {
            this.chooseText = chooseText;
        }

    }

}


