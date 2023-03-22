package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


    public class ProductList {

        /*@SerializedName("ProductData")
        @Expose
        public List<ProductDatum> productData = null;
        @SerializedName("Ack")
        @Expose
        public int ack;
        @SerializedName("msg")
        @Expose
        public String msg;

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
            @SerializedName("delivery_text")
            @Expose
            public String deliveryText;
            @SerializedName("return_refund_text")
            @Expose
            public String returnRefundText;
            @SerializedName("photos")
            @Expose
            public List<Photo> photos = null;
            @SerializedName("sizes")
            @Expose
            public List<Size> sizes = null;

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
        }*/

    @SerializedName("ProductData")
    @Expose
    private List<ProductDatum> productData = null;
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

    public class ProductDatum {

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("overall_discount")
        @Expose
        private String overallDiscount;
        @SerializedName("product_name_english")
        @Expose
        private String productNameEnglish;
        @SerializedName("product_name_bengali")
        @Expose
        private String productNameBengali;
        @SerializedName("product_details")
        @Expose
        private String productDetails;

        @SerializedName("product_photo")
        @Expose
        private String productPhoto;
        @SerializedName("product_photo2")
        @Expose
        private String productPhotoTwo;
        @SerializedName("product_photo3")
        @Expose
        private String productPhotoThree;
        @SerializedName("product_photo4")
        @Expose
        private String productPhotoFour;
        @SerializedName("stock_available")
        @Expose
        private String stockAvailable;

        @SerializedName("Packets")
        @Expose
        private List<Packet> packets = null;

        private boolean isClicked = false;
        private int selectedPos = -1;

        public String getOverallDiscount() {
            return overallDiscount;
        }

        public void setOverallDiscount(String overallDiscount) {
            this.overallDiscount = overallDiscount;
        }

        public String getStockAvailable() {
            return stockAvailable;
        }

        public void setStockAvailable(String stockAvailable) {
            this.stockAvailable = stockAvailable;
        }

        public String getProductPhotoTwo() {
            return productPhotoTwo;
        }

        public void setProductPhotoTwo(String productPhotoTwo) {
            this.productPhotoTwo = productPhotoTwo;
        }

        public String getProductPhotoThree() {
            return productPhotoThree;
        }

        public void setProductPhotoThree(String productPhotoThree) {
            this.productPhotoThree = productPhotoThree;
        }

        public String getProductPhotoFour() {
            return productPhotoFour;
        }

        public void setProductPhotoFour(String productPhotoFour) {
            this.productPhotoFour = productPhotoFour;
        }

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

        public String getProductNameBengali() {
            return productNameBengali;
        }

        public void setProductNameBengali(String productNameBengali) {
            this.productNameBengali = productNameBengali;
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

        public List<Packet> getPackets() {
            return packets;
        }

        public void setPackets(List<Packet> packets) {
            this.packets = packets;
        }
    }
}
