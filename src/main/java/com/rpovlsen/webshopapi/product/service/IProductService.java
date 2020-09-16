package com.rpovlsen.webshopapi.product.service;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface IProductService
{
    List<IProduct> getAllProducts();
    IProduct getProduct(int id) throws ProductNotFoundException;
}
