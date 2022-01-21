package com.jh.ordersmanagement.services;

import com.jh.ordersmanagement.constants.Messages;
import com.jh.ordersmanagement.entities.Product;
import com.jh.ordersmanagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> list() {
        return productRepository.findAll();
    }

    public List<Product> list(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.PRODUCT_NOT_FOUND));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product currency) {
        Product productToUpdate = get(id);
        productToUpdate.setName(currency.getName());
        productToUpdate.setCategory(currency.getCategory());
        productToUpdate.setUnitPrice(currency.getUnitPrice());
        productToUpdate.setActive(currency.isActive());

        return productRepository.save(productToUpdate);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
