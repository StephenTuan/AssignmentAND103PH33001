package com.example.assignment_and103_ph33001.Model;

import com.google.gson.annotations.SerializedName;

public class CateItem {
    @SerializedName("nameCate")
    private String nameCate;

    @SerializedName("_id")
    private String id_cate;

    public CateItem() {
    }

    public CateItem(String nameCate, String id_cate) {
        this.nameCate = nameCate;
        this.id_cate = id_cate;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }

    public String getId_cate() {
        return id_cate;
    }

    public void setId_cate(String id_cate) {
        this.id_cate = id_cate;
    }
}
