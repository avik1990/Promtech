package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersDetailsModel {

    @SerializedName("ProductData")
    @Expose
    private List<ProductDatum> productData = null;
    @SerializedName("CartData")
    @Expose
    private List<CartDatum> cartData = null;
    @SerializedName("PriceData")
    @Expose
    private PriceData priceData;
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

    public List<CartDatum> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
    }

    public PriceData getPriceData() {
        return priceData;
    }

    public void setPriceData(PriceData priceData) {
        this.priceData = priceData;
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

    public class PriceData {

        @SerializedName("total_price")
        @Expose
        private String totalPrice;

        @SerializedName("delivery_charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("wallet_balance")
        @Expose
        private String walletBalance;
        @SerializedName("pickup_date")
        @Expose
        private String pickupDate;

        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("cashback")
        @Expose
        private String cashback;

        public String getCashback() {
            return cashback;
        }

        public void setCashback(String cashback) {
            this.cashback = cashback;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }


        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public String getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(String walletBalance) {
            this.walletBalance = walletBalance;
        }

        public String getPickupDate() {
            return pickupDate;
        }

        public void setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }


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

    public class CartDatum {

        private boolean isSelectes = false;

        private String tempQuantity = "0";
        @SerializedName("cart_id")
        @Expose
        private String cartId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_photo")
        @Expose
        private String productPhoto;
        @SerializedName("product_name_english")
        @Expose
        private String productNameEnglish;
        @SerializedName("size")
        @Expose
        private String packetSize;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("quantity")
        @Expose
        private String quantity;

        @SerializedName("unit_price")
        @Expose
        private String unitPrice;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;
        @SerializedName("delivery_charge")
        @Expose
        private String delivery_charge;
        @SerializedName("delivered_quantity")
        @Expose
        private String delivered_quantity;

        public boolean isSelectes() {
            return isSelectes;
        }

        public void setSelectes(boolean selectes) {
            isSelectes = selectes;
        }

        public String getTempQuantity() {
            return tempQuantity;
        }

        public void setTempQuantity(String tempQuantity) {
            this.tempQuantity = tempQuantity;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductPhoto() {
            return productPhoto;
        }

        public void setProductPhoto(String productPhoto) {
            this.productPhoto = productPhoto;
        }

        public String getProductNameEnglish() {
            return productNameEnglish;
        }

        public void setProductNameEnglish(String productNameEnglish) {
            this.productNameEnglish = productNameEnglish;
        }

        public String getPacketSize() {
            return packetSize;
        }

        public void setPacketSize(String packetSize) {
            this.packetSize = packetSize;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }


        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public String getDelivered_quantity() {
            return delivered_quantity;
        }

        public void setDelivered_quantity(String delivered_quantity) {
            this.delivered_quantity = delivered_quantity;
        }
    }
    }