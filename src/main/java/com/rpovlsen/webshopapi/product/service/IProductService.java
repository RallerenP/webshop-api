package com.rpovlsen.webshopapi.product.service;

import com.rpovlsen.webshopapi.product.entity.IProduct;

import java.util.List;

public interface IProductService
{
    List<IProduct> getAllProducts();
}
