package com.lpufoodie.lpufoodie;

import java.util.ArrayList;

public class Restaurant {
    private String id;
    private String name;
    private double rating;
    private String address;
    private String pictureUri;
    private String subTitle;
    private String offer;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    private String keywords;
    private ArrayList<Food> foods = new ArrayList<>();

    public Restaurant() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void addFoods(Food foods) {
        this.foods.add(foods);
    }

    public String getPictureUri() {
        return this.pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}


