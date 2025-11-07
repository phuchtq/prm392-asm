package com.milkstoremobile_fronend.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String brandName;
    private String ageRange;
    private String subCategory;
    private String packageType;
    private String source;

    public Category(String source, String packageType, String subCategory, String ageRange, String brandName) {
        this.source = source;
        this.packageType = packageType;
        this.subCategory = subCategory;
        this.ageRange = ageRange;
        this.brandName = brandName;
    }

    public Category(String id, String brandName, String ageRange, String subCategory, String packageType, String source) {
        this.id = id;
        this.brandName = brandName;
        this.ageRange = ageRange;
        this.subCategory = subCategory;
        this.packageType = packageType;
        this.source = source;
    }

    public Category(String all) {
    }

    public Category(String type, String name) {
    }

    public String getId() {
        return id;
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
        this.id = id;
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

