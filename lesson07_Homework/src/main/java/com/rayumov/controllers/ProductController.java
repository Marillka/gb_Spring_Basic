package com.rayumov.controllers;

import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/products/cost/change_cost")
    public void changeScore(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changeScore(productId, delta);
    }

    @GetMapping("/products/cost_between")
    public List<Product> findProductsByCostBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "10000") Integer max) {
        return productService.findProductsByCostBetween(min, max);
    }

    @GetMapping("/products/cost_after")
    public List<Product> findProductsByCostAfter(@RequestParam(defaultValue = "0") Integer min) {
        return productService.findProductsByCostAfter(min);
    }

    @GetMapping("/products/cost_before")
    public List<Product> findProductByCostBefore(@RequestParam(defaultValue = "100") Integer max) {
        return productService.findProductByCostBefore(max);
    }


}
