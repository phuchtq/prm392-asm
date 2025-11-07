package com.milkstoremobile_fronend.models.cart;
import java.io.Serializable;

public class ProductDetailOfCart implements Serializable {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String image;
    private String description;
    private String statusDescription;

    // Constructor
    public ProductDetailOfCart(String productId, String productName, int quantity, double price, String image, String description, String statusDescription) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.description = description;
        this.statusDescription = statusDescription;
    }

    // Getters và Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
