package com.milkstoremobile_fronend.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String description;
    private String categoryId;
    private String image;
    private String statusDescription;

    // Constructor mặc định

    public Product(String productName , double price,String image) {

        this.productName = productName;
        this.price = price;
        this.image = image;

    }

    public Product(String name, String categoryId, double price, String description, String image){
        this.productName = name;
        this.categoryId = categoryId;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Product(String image, String categoryId, String description, double price, int quantity, String productName) {
        this.image = image;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productName = productName;
    }

    // Constructor đầy đủ tham số
    public Product(String productId, String productName, int quantity, double price, String description, String categoryId, String image) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.image = image;
    }


    public Product(String name, double price, String type, String url) {
    }

    // Getter và Setter
    public String getId() {
        return productId;
    }

    public void setId(String id) {
        this.productId = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
