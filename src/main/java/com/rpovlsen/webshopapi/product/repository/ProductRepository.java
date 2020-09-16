package com.rpovlsen.webshopapi.product.repository;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.entity.Product;
import com.rpovlsen.webshopapi.product.exceptions.ProductNotFoundException;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository()
public class ProductRepository implements IProductRepository
{
    private final JdbcTemplate template;

    @Autowired()
    public ProductRepository(JdbcTemplate jdbcTemplate)
    {
        this.template = jdbcTemplate;
    }


    @Override
    public IProduct create() throws ProductNotFoundException {
        return null; // Not Implemented
    }

    @Override
    public IProduct getById(int id) throws ProductNotFoundException {
        String SQL = "SELECT * FROM products WHERE id = ?";
        SqlRowSet result = template.queryForRowSet(SQL, id);

        if (result.next())
        {
            return load(result);
        }
        else
        {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public List<IProduct> find() {
        String SQL = "SELECT * FROM products";
        SqlRowSet result = template.queryForRowSet(SQL);

        List<IProduct> products = new ArrayList<>();
        while(result.next()) products.add(load(result));

        return products;
    }

    @Override
    public IProduct update() throws ProductNotFoundException {
        return null;
    }

    @Override
    public boolean delete(int id) throws ProductNotFoundException {
        return false;
    }

    private IProduct load(SqlRowSet rowSet)
    {
        IProduct product = new Product(rowSet.getInt("id"));
        product.setName(rowSet.getString("name"));
        product.setProductCode(rowSet.getString("product_code"));
        product.setPrice(rowSet.getDouble("price"));
        return product;
    }
}
