package com.rpovlsen.webshopapi.product;

import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.service.IProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping(path="products")
@CrossOrigin(origins = "*")
public class ProductController
{
    private final IProductService productService;

    public ProductController(IProductService productService)
    {
        this.productService = productService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getProducts()
    {
        List<IProduct> products = this.productService.getAllProducts();

        JSONArray response = new JSONArray();

        for (IProduct product : products)
        {
            JSONObject jsonProduct = new JSONObject();

            jsonProduct.put("name", product.getName());
            jsonProduct.put("code", product.getProductCode());
            jsonProduct.put("price", product.getPrice());

            response.put(jsonProduct);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

}
