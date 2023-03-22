package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganicBenefit {


    @SerializedName("AboutData")
    @Expose
    private AboutData aboutData;

    public AboutData getAboutData() {
        return aboutData;
    }

    public void setAboutData(AboutData aboutData) {
        this.aboutData = aboutData;
    }

    public class AboutData {

        @SerializedName("content_id")
        @Expose
        private String contentId;
        @SerializedName("content_title")
        @Expose

        private String contentTitle;

        @SerializedName("content_photo")
        @Expose

        private String content_photo;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("Ack")
        @Expose
        private Integer ack;
        @SerializedName("msg")
        @Expose
        private String msg;

        public String getContent_photo() {
            return content_photo;
        }

        public void setContent_photo(String content_photo) {
            this.content_photo = content_photo;
        }

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
