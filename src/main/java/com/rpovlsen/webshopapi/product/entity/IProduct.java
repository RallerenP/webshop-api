package com.rpovlsen.webshopapi.product.entity;

public interface IProduct
{
    int getId();

    String getName();
    void setName(String name);

    String getProductCode();
    void setProductCode(String productCode);

    double getPrice();
    void setPrice(double price);
}
