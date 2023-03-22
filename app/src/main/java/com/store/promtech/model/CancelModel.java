package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelModel {
    @SerializedName("AboutData")
    @Expose
    public ContactData contactData;

    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public class ContactData {

        @SerializedName("content_id")
        @Expose
        public String content_id;
        @SerializedName("content_title")
        @Expose
        public String content_title;
        @SerializedName("content")
        @Expose
        public String content;

        @SerializedName("Ack")
        @Expose
        public Integer ack;
        @SerializedName("msg")
        @Expose
        public String msg;

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getContent_title() {
            return content_title;
        }

        public void setContent_title(String content_title) {
            this.content_title = content_title;
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
