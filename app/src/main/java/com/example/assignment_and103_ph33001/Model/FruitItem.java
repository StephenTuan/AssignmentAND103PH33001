package com.example.assignment_and103_ph33001.Model;

import com.google.gson.annotations.SerializedName;

public class FruitItem {
    @SerializedName("name")
    private String name;

    @SerializedName("_id")
    private String id;
    @SerializedName("id_category")
    private Object id_category; // Changed to Object to handle nested category object
    
    @SerializedName("distributor")
    private String distributor;
    
    @SerializedName("price")
    private String price;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("rate")
    private String rate;
    
    @SerializedName("imageFruist")
    private String imageFruist;

    public FruitItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FruitItem(String id) {
        this.id = id;
    }

    public FruitItem(String nameFruist, Object id_category, String distributorFruist, String priceFruist, String descriptionFruist, String rateFruist) {
        this.name = nameFruist;
        this.id_category = id_category;
        this.distributor = distributorFruist;
        this.price = priceFruist;
        this.description = descriptionFruist;
        this.rate = rateFruist;
    }

    public String getNameFruist() {
        return name;
    }

    public void setNameFruist(String nameFruist) {
        this.name = nameFruist;
    }

    public Object getId_category() {
        return id_category;
    }

    public void setId_category(Object id_category) {
        this.id_category = id_category;
    }

    public String getDistributorFruist() {
        return distributor;
    }

    public void setDistributorFruist(String distributorFruist) {
        this.distributor = distributorFruist;
    }

    public String getPriceFruist() {
        return price;
    }

    public void setPriceFruist(String priceFruist) {
        this.price = priceFruist;
    }

    public String getDescriptionFruist() {
        return description;
    }

    public void setDescriptionFruist(String descriptionFruist) {
        this.description = descriptionFruist;
    }

    public String getRateFruist() {
        return rate;
    }

    public void setRateFruist(String rateFruist) {
        this.rate = rateFruist;
    }

    public String getImageFruist() {
        return imageFruist;
    }

    public void setImageFruist(String imageFruist) {
        this.imageFruist = imageFruist;
    }

}
