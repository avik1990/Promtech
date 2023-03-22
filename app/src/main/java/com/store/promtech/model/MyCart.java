package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Avik on 27-08-2017.
 */

public class MyCart {

    public List<String> total_arr;
    @SerializedName("CartData")
    @Expose
    public List<CartDatum> cartData = null;
    @SerializedName("PriceData")
    @Expose
    public PriceData priceData;
    @SerializedName("Ack")
    @Expose
    public Integer ack;
    @SerializedName("msg")
    @Expose
    public String msg;

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

    public class PriceData {

        @SerializedName("total_price")
        @Expose
        public String totalPrice;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("delivery_charge")
        @Expose
        public String delivery_charge;
        @SerializedName("grand_total")
        @Expose
        public String grand_total;

        @SerializedName("total_quantity")
        @Expose
        public String total_quantity;
        @SerializedName("cashback")
        @Expose
        public String cashback;

        public String getCashback() {
            return cashback;
        }

        public void setCashback(String cashback) {
            this.cashback = cashback;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public String getGrand_total() {
            return grand_total;
        }

        public void setGrand_total(String grand_total) {
            this.grand_total = grand_total;
        }

        public String getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(String total_quantity) {
            this.total_quantity = total_quantity;
        }
    }

    public class CartDatum {
        @SerializedName("cart_id")
        @Expose
        public String cartId;
        @SerializedName("product_photo")
        @Expose
        public String productPhoto;
        @SerializedName("product_name_english")
        @Expose
        public String productName;


        @SerializedName("quantity")
        @Expose
        public String quantity;

        @SerializedName("unit_price")
        @Expose
        public String unitPrice;
        @SerializedName("subtotal")
        @Expose
        public String subtotal;
        @SerializedName("delivery_charge")
        @Expose
        public String delivery_charge;


        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getProductPhoto() {
            return productPhoto;
        }

        public void setProductPhoto(String productPhoto) {
            this.productPhoto = productPhoto;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

    }


}
