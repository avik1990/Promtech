package com.store.promtech.model;

public class ReturnList {
    String product_id, quentity;

    public ReturnList() {
    }

    public ReturnList(String product_id, String quentity) {
        this.product_id = product_id;
        this.quentity = quentity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }
}
