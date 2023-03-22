package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("CategoryData")
    @Expose
    private List<CategoryDatum> categoryData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("DeliveryData")
    @Expose
    private DeliveryData deliveryData;

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
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

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public class DeliveryData {

        @SerializedName("content")
        @Expose
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }


    public class CategoryDatum {

        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("category_photo")
        @Expose
        public String categoryPhoto;
        @SerializedName("no_of_subcategory")
        @Expose
        public String noofSubcaterory;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryPhoto() {
            return categoryPhoto;
        }

        public void setCategoryPhoto(String categoryPhoto) {
            this.categoryPhoto = categoryPhoto;
        }

        public String getNoofSubcaterory() {
            return noofSubcaterory;
        }

        public void setNoofSubcaterory(String noofSubcaterory) {
            this.noofSubcaterory = noofSubcaterory;
        }
    }

}
