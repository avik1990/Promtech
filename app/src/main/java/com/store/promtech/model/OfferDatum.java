
package com.store.promtech.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class OfferDatum {

    @SerializedName("offer_id")
    private String mOfferId;
    @SerializedName("offer_photo")
    private String mOfferPhoto;
    @SerializedName("offer_text")
    private String mOfferText;

    public String getOfferId() {
        return mOfferId;
    }

    public void setOfferId(String offerId) {
        mOfferId = offerId;
    }

    public String getOfferPhoto() {
        return mOfferPhoto;
    }

    public void setOfferPhoto(String offerPhoto) {
        mOfferPhoto = offerPhoto;
    }

    public String getOfferText() {
        return mOfferText;
    }

    public void setOfferText(String offerText) {
        mOfferText = offerText;
    }

}
