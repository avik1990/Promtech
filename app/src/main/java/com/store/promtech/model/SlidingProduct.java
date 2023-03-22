package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlidingProduct {

    @SerializedName("ProductData")
    @Expose
    private List<ProductDatum> productData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<ProductDatum> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductDatum> productData) {
        this.productData = productData;
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

    public class ProductDatum {

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_name_english")
        @Expose
        private String productNameEnglish;
        @SerializedName("stock_available")
        @Expose
        private String stockAvailable;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
        @SerializedName("product_photo")
        @Expose
        private String productPhoto;
        @SerializedName("product_photo2")
        @Expose
        private String productPhoto2;
        @SerializedName("product_photo3")
        @Expose
        private String productPhoto3;
        @SerializedName("product_photo4")
        @Expose
        private String productPhoto4;
        @SerializedName("Packets")
        @Expose
        private List<Packet> packets = null;


        private boolean isClicked = false;
        private int selectedPos = -1;

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

        public int getSelectedPos() {
            return selectedPos;
        }

        public void setSelectedPos(int selectedPos) {
            this.selectedPos = selectedPos;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductNameEnglish() {
            return productNameEnglish;
        }

        public void setProductNameEnglish(String productNameEnglish) {
            this.productNameEnglish = productNameEnglish;
        }

        public String getStockAvailable() {
            return stockAvailable;
        }

        public void setStockAvailable(String stockAvailable) {
            this.stockAvailable = stockAvailable;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getProductPhoto() {
            return productPhoto;
        }

        public void setProductPhoto(String productPhoto) {
            this.productPhoto = productPhoto;
        }

        public String getProductPhoto2() {
            return productPhoto2;
        }

        public void setProductPhoto2(String productPhoto2) {
            this.productPhoto2 = productPhoto2;
        }

        public String getProductPhoto3() {
            return productPhoto3;
        }

        public void setProductPhoto3(String productPhoto3) {
            this.productPhoto3 = productPhoto3;
        }

        public String getProductPhoto4() {
            return productPhoto4;
        }

        public void setProductPhoto4(String productPhoto4) {
            this.productPhoto4 = productPhoto4;
        }

        public List<Packet> getPackets() {
            return packets;
        }

        public void setPackets(List<Packet> packets) {
            this.packets = packets;
        }

    }

    public class Packet {

        @SerializedName("packet_id")
        @Expose
        private String packetId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("packet_size")
        @Expose
        private String packetSize;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("original_price")
        @Expose
        private String originalPrice;

        public String getPacketId() {
            return packetId;
        }

        public void setPacketId(String packetId) {
            this.packetId = packetId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPacketSize() {
            return packetSize;
        }

        public void setPacketSize(String packetSize) {
            this.packetSize = packetSize;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

    }

}
