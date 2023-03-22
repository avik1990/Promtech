package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialMedia {
    @SerializedName("SocialMediaData")
    @Expose
    private SocialMediaData socialMediaData;

    public SocialMediaData getSocialMediaData() {
        return socialMediaData;
    }

    public void setSocialMediaData(SocialMediaData socialMediaData) {
        this.socialMediaData = socialMediaData;
    }

    public class SocialMediaData {

        @SerializedName("facebook_link")
        @Expose
        private String facebookLink;
        @SerializedName("twitter_link")
        @Expose
        private String twitterLink;
        @SerializedName("instagram_link")
        @Expose
        private String instagramLink;
        @SerializedName("pinterest_link")
        @Expose
        private String pinterestLink;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("Ack")
        @Expose
        private Integer ack;
        @SerializedName("msg")
        @Expose
        private String msg;

        public String getFacebookLink() {
            return facebookLink;
        }

        public void setFacebookLink(String facebookLink) {
            this.facebookLink = facebookLink;
        }

        public String getTwitterLink() {
            return twitterLink;
        }

        public void setTwitterLink(String twitterLink) {
            this.twitterLink = twitterLink;
        }

        public String getInstagramLink() {
            return instagramLink;
        }

        public void setInstagramLink(String instagramLink) {
            this.instagramLink = instagramLink;
        }

        public String getPinterestLink() {
            return pinterestLink;
        }

        public void setPinterestLink(String pinterestLink) {
            this.pinterestLink = pinterestLink;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
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
