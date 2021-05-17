package com.example.shokke_app.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private int id;
    private String username;
    private String idProduct;
    private int count;
    private String timeCreate;

    public Cart(int id, String username, String idProduct, int count, String timeCreate) {
        this.id = id;
        this.username = username;
        this.idProduct = idProduct;
        this.count = count;
        this.timeCreate = timeCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }
}
