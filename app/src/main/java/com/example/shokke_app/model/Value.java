package com.example.shokke_app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Value implements Serializable {
    private boolean success;
    private ArrayList<Product> products;

    public Value(boolean success, ArrayList<Product> products) {
        this.success = success;
        this.products = products;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
