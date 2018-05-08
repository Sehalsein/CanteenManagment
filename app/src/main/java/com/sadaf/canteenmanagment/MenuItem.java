package com.sadaf.canteenmanagment;

import java.io.Serializable;

/**
 * Created by sehalsein on 08/05/18.
 */

public class MenuItem implements Serializable {

    private String id;
    private String name;
    private Double price;
    private String imageUrl;
    private String category;


    public MenuItem() {
    }

    public MenuItem(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public MenuItem(String id, String name, Double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public MenuItem(String id, String name, Double price, String imageUrl, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
