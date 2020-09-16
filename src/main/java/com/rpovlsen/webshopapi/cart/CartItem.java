package com.rpovlsen.webshopapi.cart;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import org.json.JSONObject;

import java.util.Objects;

public class CartItem
{
    private IProduct product;
    private int amount;


    public CartItem(IProduct product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public IProduct getProduct() {
        return product;
    }

    public void setProduct(IProduct product) {
        this.product = product;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("product", product.toJSON());
        json.put("amount", amount);
        return json;
    }
}
