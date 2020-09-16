package com.rpovlsen.webshopapi.product.repository;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface IProductRepository
{
    IProduct create() throws ProductNotFoundException;

    IProduct getById(int id) throws ProductNotFoundException;
    List<IProduct> find();

    IProduct update() throws ProductNotFoundException;

    boolean delete(int id) throws ProductNotFoundException;
}
