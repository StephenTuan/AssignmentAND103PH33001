package com.example.assignment_and103_ph33001.Model;

public class FruitItem {
    private String nameFruist;

    private String id_category;
    private String distributorFruist;
    private String priceFruist;
    private String descriptionFruist;
    private String rateFruist;

    public FruitItem() {
    }

    public FruitItem(String nameFruist, String id_category, String distributorFruist, String priceFruist, String descriptionFruist, String rateFruist) {
        this.nameFruist = nameFruist;
        this.id_category = id_category;
        this.distributorFruist = distributorFruist;
        this.priceFruist = priceFruist;
        this.descriptionFruist = descriptionFruist;
        this.rateFruist = rateFruist;
    }

    public String getNameFruist() {
        return nameFruist;
    }

    public void setNameFruist(String nameFruist) {
        this.nameFruist = nameFruist;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getDistributorFruist() {
        return distributorFruist;
    }

    public void setDistributorFruist(String distributorFruist) {
        this.distributorFruist = distributorFruist;
    }

    public String getPriceFruist() {
        return priceFruist;
    }

    public void setPriceFruist(String priceFruist) {
        this.priceFruist = priceFruist;
    }

    public String getDescriptionFruist() {
        return descriptionFruist;
    }

    public void setDescriptionFruist(String descriptionFruist) {
        this.descriptionFruist = descriptionFruist;
    }

    public String getRateFruist() {
        return rateFruist;
    }

    public void setRateFruist(String rateFruist) {
        this.rateFruist = rateFruist;
    }
}
