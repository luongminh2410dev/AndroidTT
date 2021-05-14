package com.example.shokke_app.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String _id;
    private int id;
    private String name;
    private String description;
    private float price;
    private String img;
    private int sortOut;
    private int total;
//    private int promo;
    private int id_producer;
    private int __v;

    public Product(String _id, int id, String name, String description, float price, String img, int sortOut, int total, int id_producer, int __v) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.sortOut = sortOut;
        this.total = total;
        this.id_producer = id_producer;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSortOut() {
        return sortOut;
    }

    public void setSortOut(int sortOut) {
        this.sortOut = sortOut;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

//    public int getPromo() {
//        return promo;
//    }
//
//    public void setPromo(int promo) {
//        this.promo = promo;
//    }

    public int getId_producer() {
        return id_producer;
    }

    public void setId_producer(int id_producer) {
        this.id_producer = id_producer;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
