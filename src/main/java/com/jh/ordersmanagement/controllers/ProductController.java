package com.jh.ordersmanagement.controllers;

import com.jh.ordersmanagement.constants.Routes;
import com.jh.ordersmanagement.entities.Product;
import com.jh.ordersmanagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.PRODUCTS, produces = (MediaType.APPLICATION_JSON_VALUE))
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.list();
    }

    @GetMapping(Routes.ID)
    public Product get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping(Routes.ID)
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping(Routes.ID)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
