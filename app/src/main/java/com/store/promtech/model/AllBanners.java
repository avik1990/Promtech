package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllBanners {






        @SerializedName("Delivery4Hours")
        @Expose
        private Delivery4Hours delivery4Hours;
        @SerializedName("ReferEarn")
        @Expose
        private ReferEarn referEarn;
        @SerializedName("WhyUChoose")
        @Expose
        private WhyUChoose whyUChoose;
        @SerializedName("OrganicBenefit")
        @Expose
        private OrganicBenefit organicBenefit;

        public Delivery4Hours getDelivery4Hours() {
            return delivery4Hours;
        }

        public void setDelivery4Hours(Delivery4Hours delivery4Hours) {
            this.delivery4Hours = delivery4Hours;
        }

        public ReferEarn getReferEarn() {
            return referEarn;
        }

        public void setReferEarn(ReferEarn referEarn) {
            this.referEarn = referEarn;
        }

        public WhyUChoose getWhyUChoose() {
            return whyUChoose;
        }

        public void setWhyUChoose(WhyUChoose whyUChoose) {
            this.whyUChoose = whyUChoose;
        }

        public OrganicBenefit getOrganicBenefit() {
            return organicBenefit;
        }

        public void setOrganicBenefit(OrganicBenefit organicBenefit) {
            this.organicBenefit = organicBenefit;
        }




    public class Delivery4Hours {

        @SerializedName("photo")
        @Expose
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }


    public class OrganicBenefit {

        @SerializedName("photo")
        @Expose
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }


    public class ReferEarn {

        @SerializedName("photo")
        @Expose
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }



    public class WhyUChoose {

        @SerializedName("photo")
        @Expose
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }
}
