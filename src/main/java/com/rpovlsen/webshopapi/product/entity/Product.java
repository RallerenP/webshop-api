package com.rpovlsen.webshopapi.product.entity;

public class Product implements IProduct
{
    private String name;
    private String productCode;
    private double price;
    private int id;
    
    public Product(int id)
    {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getProductCode() {
        return this.productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }


}
