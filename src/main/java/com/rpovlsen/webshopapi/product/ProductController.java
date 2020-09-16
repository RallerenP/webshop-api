package com.rpovlsen.webshopapi.product;

import com.rpovlsen.webshopapi.cart.Cart;
import com.rpovlsen.webshopapi.product.entity.IProduct;
import com.rpovlsen.webshopapi.product.entity.Product;
import com.rpovlsen.webshopapi.product.exceptions.ProductNotFoundException;
import com.rpovlsen.webshopapi.product.service.IProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            response.put(product.toJSON());
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") int id)
    {
        try
        {
            IProduct product = this.productService.getProduct(id);
            return new ResponseEntity<>(product.toJSON().toString(), HttpStatus.OK);
        }
        catch (ProductNotFoundException e)
        {
            JSONObject resp = new JSONObject();
            resp.put("error", "The request product (id: " + id + ") could not be found!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<String> getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart =  (Cart) session.getAttribute("cart");

        if (cart == null) session.setAttribute("cart", new Cart());

        cart = (Cart) session.getAttribute("cart");

        return new ResponseEntity<>(cart.toJSON().toString(), HttpStatus.OK);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addProductToCart(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Cart cart =  (Cart) session.getAttribute("cart");



        if (cart == null) session.setAttribute("cart", new Cart());

        cart = (Cart) session.getAttribute("cart");

        try
        {
            IProduct product = this.productService.getProduct(id);

            cart.addToCart(product);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (ProductNotFoundException e)
        {
            JSONObject resp = new JSONObject();
            resp.put("error", "The specified product ( id: " + id + " ) was not found");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
