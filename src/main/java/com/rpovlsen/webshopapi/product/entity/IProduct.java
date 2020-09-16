package com.rpovlsen.webshopapi.product.entity;

import org.json.JSONObject;

public interface IProduct
{
    int getId();

    String getName();
    void setName(String name);

    String getProductCode();
    void setProductCode(String productCode);

    double getPrice();
    void setPrice(double price);

    JSONObject toJSON();

    boolean equals(Object o);
}
