package com.rpovlsen.webshopapi.product.service;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.exceptions.ProductNotFoundException;
import com.rpovlsen.webshopapi.product.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class ProductService implements IProductService
{
    private final IProductRepository productRepository;

    @Autowired()
    public ProductService(IProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public List<IProduct> getAllProducts() {
        return this.productRepository.find();
    }

    @Override
    public IProduct getProduct(int id) throws ProductNotFoundException {
        return this.productRepository.getById(id);
    }
}
