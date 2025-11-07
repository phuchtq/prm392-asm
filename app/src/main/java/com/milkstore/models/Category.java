package com.milkstore.models;

import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    // Match the exact field names from your backend
    @SerializedName("categoriesID")
    private String categoriesID;

    @SerializedName("ageRange")
    private String ageRange;

    @SerializedName("brandName")
    private String brandName;

    @SerializedName("createDate")
    private String createDate;

    @SerializedName("packageType")
    private String packageType;

    @SerializedName("source")
    private String source;

    @SerializedName("subCategories")  // This matches your backend!
    private String subCategory;

    @SerializedName("updateDate")
    private String updateDate;

    public Category(String source, String packageType, String subCategory, String ageRange, String brandName) {
        this.source = source;
        this.packageType = packageType;
        this.subCategory = subCategory;
        this.ageRange = ageRange;
        this.brandName = brandName;
    }

    public Category(String id, String brandName, String ageRange, String subCategory, String packageType, String source) {
        this.categoriesID = id;
        this.brandName = brandName;
        this.ageRange = ageRange;
        this. subCategory= subCategory;
        this.packageType = packageType;
        this.source = source;
    }

    public Category(String all) {
    }

    public Category(String type, String name) {
    }

    public String getId() {
        return this.categoriesID;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setId(String id) {
        this.categoriesID = id;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getPackageType() {
        return packageType;
    }

    public String getSource() {
        return source;
    }



}

