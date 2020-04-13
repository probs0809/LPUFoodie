package com.lpufoodie.lpufoodie;

public class Food {
    private String id;
    private String name;
    private double cost;
    private String pictureUri;
    private boolean type;
    private String category;
    private int count;

    public Food() {

    }

    public Food(String id, String name, double cost, String pictureUri, boolean type, String category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.pictureUri = pictureUri;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public boolean isType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
