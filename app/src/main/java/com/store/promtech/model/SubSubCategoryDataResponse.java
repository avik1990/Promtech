package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubSubCategoryDataResponse {


    @SerializedName("SubSubCategoryData")
    @Expose
    private List<SubCategoryDatum> subCategoryData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("banner_photo")
    @Expose
    private String banner_photo;

    public String getBanner_photo() {
        return banner_photo;
    }

    public void setBanner_photo(String banner_photo) {
        this.banner_photo = banner_photo;
    }

    public List<SubCategoryDatum> getSubCategoryData() {
        return subCategoryData;
    }

    public void setSubCategoryData(List<SubCategoryDatum> subCategoryData) {
        this.subCategoryData = subCategoryData;
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

    public class SubCategoryDatum {

        @SerializedName("subsubcategory_id")
        @Expose
        private String subcategoryId;
        @SerializedName("subsubcategory_name")
        @Expose
        private String subcategoryName;
        @SerializedName("subsubcategory_photo")
        @Expose
        private String subcategoryPhoto;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(String subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public String getSubcategoryName() {
            return subcategoryName;
        }

        public void setSubcategoryName(String subcategoryName) {
            this.subcategoryName = subcategoryName;
        }

        public String getSubcategoryPhoto() {
            return subcategoryPhoto;
        }

        public void setSubcategoryPhoto(String subcategoryPhoto) {
            this.subcategoryPhoto = subcategoryPhoto;
        }

    }
}
