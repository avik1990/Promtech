package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewSuggession {

    @SerializedName("SuggestionData")
    @Expose
    private SuggestionData suggestionData;

    public SuggestionData getSuggestionData() {
        return suggestionData;
    }

    public void setSuggestionData(SuggestionData suggestionData) {
        this.suggestionData = suggestionData;
    }

    public class SuggestionData {

        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("Ack")
        @Expose
        private Integer ack;
        @SerializedName("msg")
        @Expose
        private String msg;

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
