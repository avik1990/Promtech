package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrders {

    @SerializedName("OrderData")
    @Expose
    private List<OrderDatum> orderData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<OrderDatum> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDatum> orderData) {
        this.orderData = orderData;
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

    public class OrderDatum {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("total_items")
        @Expose
        private String total_items;
        @SerializedName("total_price")
        @Expose
        private String total_price;
        @SerializedName("order_date")
        @Expose
        private String order_date;
        @SerializedName("payment_status")
        @Expose
        private String payment_status;
        @SerializedName("cancel_request_sent")
        @Expose
        private String cancel_request_sent;
        @SerializedName("delivery_status")
        @Expose
        private String delivery_status;
        @SerializedName("return_possible")
        @Expose
        private String return_possible;
        @SerializedName("return_request_sent")
        @Expose
        private String return_request_sent;
        @SerializedName("order_status")
        @Expose
        private String order_status;
        @SerializedName("return_confirm")
        @Expose
        private String return_confirm ;


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTotal_items() {
            return total_items;
        }

        public void setTotal_items(String total_items) {
            this.total_items = total_items;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getCancel_request_sent() {
            return cancel_request_sent;
        }

        public void setCancel_request_sent(String cancel_request_sent) {
            this.cancel_request_sent = cancel_request_sent;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getReturn_possible() {
            return return_possible;
        }

        public void setReturn_possible(String return_possible) {
            this.return_possible = return_possible;
        }

        public String getReturn_request_sent() {
            return return_request_sent;
        }

        public void setReturn_request_sent(String return_request_sent) {
            this.return_request_sent = return_request_sent;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }


        public String getReturn_confirm() {
            return return_confirm;
        }

        public void setReturn_confirm(String return_confirm) {
            this.return_confirm = return_confirm;
        }
    }

}
