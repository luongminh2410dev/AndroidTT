package com.example.shokke_app.model;

public class DetailCart {
    private int id;
    private String userName;
    private Product product;
    private int count;

    public DetailCart(int id, String userName, Product product, int count) {
        this.id = id;
        this.userName = userName;
        this.product = product;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
