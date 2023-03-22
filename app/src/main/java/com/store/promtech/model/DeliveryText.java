package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryText {

    @SerializedName("TermData")
    @Expose
    private TermData termData;

    public TermData getTermData() {
        return termData;
    }

    public void setTermData(TermData termData) {
        this.termData = termData;
    }

    public class TermData {

        @SerializedName("content_id")
        @Expose
        private String contentId;
        @SerializedName("content_title")
        @Expose
        private String contentTitle;
        @SerializedName("content_photo")
        @Expose
        private String contentPhoto;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("Ack")
        @Expose
        private Integer ack;
        @SerializedName("msg")
        @Expose
        private String msg;

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getContentPhoto() {
            return contentPhoto;
        }

        public void setContentPhoto(String contentPhoto) {
            this.contentPhoto = contentPhoto;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

    }

}
