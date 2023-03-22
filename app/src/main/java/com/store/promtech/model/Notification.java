package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("NotificationData")
    @Expose
    private NotificationData notificationData;

    public NotificationData getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(NotificationData notificationData) {
        this.notificationData = notificationData;
    }

    public class NotificationData {

        @SerializedName("notification_text")
        @Expose
        private String notificationText;
        @SerializedName("Ack")
        @Expose
        private Integer ack;
        @SerializedName("msg")
        @Expose
        private String msg;

        public String getNotificationText() {
            return notificationText;
        }

        public void setNotificationText(String notificationText) {
            this.notificationText = notificationText;
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
