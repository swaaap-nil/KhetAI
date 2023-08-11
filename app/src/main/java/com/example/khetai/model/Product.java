package com.example.khetai.model;

import java.util.Objects;

public class Product {

    private String productName;
    private String productCountry;
    private Double productPrice;
    private int productImage;
    private String productDesc;

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }



    public Product(String productName, Double productPrice, int productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public Product(String productName, Double productPrice,  String productDesc,int productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDesc = productDesc;
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCountry() {
        return productCountry;
    }

    public void setProductCountry(String productCountry) {
        this.productCountry = productCountry;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}
