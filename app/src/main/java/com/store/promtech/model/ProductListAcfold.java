package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListAcfold {
    @SerializedName("ProductData")
        @Expose
        public List<ProductDatum> productData = null;
        @SerializedName("Ack")
        @Expose
        public int ack;
        @SerializedName("msg")
        @Expose
        public String msg;
    public Object ProductDatum;

    public List<ProductDatum> getProductData() {
            return productData;
        }

        public void setProductData(List<ProductDatum> productData) {
            this.productData = productData;
        }

        public int getAck() {
            return ack;
        }

        public void setAck(int ack) {
            this.ack = ack;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public class Photo {

            @SerializedName("photo_id")
            @Expose
            public String photoId;
            @SerializedName("photo")
            @Expose
            public String photo;

            public String getPhotoId() {
                return photoId;
            }

            public void setPhotoId(String photoId) {
                this.photoId = photoId;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

        public class ProductDatum {

            @SerializedName("product_id")
            @Expose
            public String productId;
            @SerializedName("product_name_english")
            @Expose
            public String productNameEnglish;
            @SerializedName("stock_available")
            @Expose
            public String stockAvailable;
            @SerializedName("product_short_details")
            @Expose
            public String productShortDetails;
            @SerializedName("model_no")
            @Expose
            private String model_no;
            @SerializedName("product_long_details")
            @Expose
            public String productLongDetails;
            @SerializedName("original_price")
            @Expose
            public String originalPrice;
            @SerializedName("discount")
            @Expose
            public String discount;
            @SerializedName("sale_price")
            @Expose
            public String salePrice;
            @SerializedName("stock")
            @Expose
            public String stock;
            @SerializedName("delivery_text")
            @Expose
            public String deliveryText;
            @SerializedName("return_refund_text")
            @Expose
            public String returnRefundText;
            @SerializedName("seller_id")
            @Expose
            public String seller_id;
            @SerializedName("photos")
            @Expose
            public List<Photo> photos = null;
            @SerializedName("sizes")
            @Expose
            public List<Size> sizes = null;
            @SerializedName("colors")
            @Expose
            public List<Colors> colors = null;

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

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getStockAvailable() {
                return stockAvailable;
            }

            public void setStockAvailable(String stockAvailable) {
                this.stockAvailable = stockAvailable;
            }

            public String getProductShortDetails() {
                return productShortDetails;
            }

            public void setProductShortDetails(String productShortDetails) {
                this.productShortDetails = productShortDetails;
            }

            public String getProductLongDetails() {
                return productLongDetails;
            }

            public void setProductLongDetails(String productLongDetails) {
                this.productLongDetails = productLongDetails;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getModel_no() {
                return model_no;
            }

            public void setModel_no(String model_no) {
                this.model_no = model_no;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getDeliveryText() {
                return deliveryText;
            }

            public void setDeliveryText(String deliveryText) {
                this.deliveryText = deliveryText;
            }

            public String getReturnRefundText() {
                return returnRefundText;
            }

            public void setReturnRefundText(String returnRefundText) {
                this.returnRefundText = returnRefundText;
            }

            public List<Photo> getPhotos() {
                return photos;
            }

            public void setPhotos(List<Photo> photos) {
                this.photos = photos;
            }

            public List<Size> getSizes() {
                return sizes;
            }

            public void setSizes(List<Size> sizes) {
                this.sizes = sizes;
            }

            public List<Colors> getColors() {
                return colors;
            }

            public void setColors(List<Colors> colors) {
                this.colors = colors;
            }
        }


        public class Size {

            @SerializedName("size_id")
            @Expose
            public String sizeId;
            @SerializedName("size")
            @Expose
            public String size;

            public String getSizeId() {
                return sizeId;
            }

            public void setSizeId(String sizeId) {
                this.sizeId = sizeId;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }
        }
        public class Colors {

            @SerializedName("color_id")
            @Expose
            public String color_id;
            @SerializedName("color")
            @Expose
            public String color;

            public String getColor_id() {
                return color_id;
            }

            public void setColor_id(String color_id) {
                this.color_id = color_id;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
}
