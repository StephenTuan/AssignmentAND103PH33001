package com.example.assignment_and103_ph33001.Model;

public class FruitItem {
    private String name;
    private String price;
    private String weight;
    private int imageResourceId; // ID của hình ảnh trong drawable
    private boolean isFavorite;

    public FruitItem(String name, String price, String weight, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.imageResourceId = imageResourceId;
        this.isFavorite = false; // Mặc định chưa yêu thích
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
