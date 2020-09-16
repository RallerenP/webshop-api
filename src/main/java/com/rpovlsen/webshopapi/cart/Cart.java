package com.rpovlsen.webshopapi.cart;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cart
{
    private List<CartItem> items = new ArrayList<>();

    public void addToCart(IProduct product)
    {
        CartItem item = findCartItem(product);
        if (item == null)
        {
            items.add(new CartItem(product, 1));
        }
        else
        {
            item.setAmount(item.getAmount() + 1);
        }
    }

    public CartItem findCartItem(IProduct product)
    {
        for (CartItem item : this.items)
        {
            if (item.getProduct().equals(product))
            {
                return item;
            }
        }

        return null;
    }

    public JSONArray toJSON()
    {
        JSONArray json = new JSONArray();
        for (CartItem item : items)
            json.put(item.toJSON());

        return json;
    }
}
