package com.rayumov.controllers;

import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> startPage(
            @RequestParam(name = "minFilter", defaultValue = "0") Integer minPrice,
            @RequestParam(name = "maxFilter", required = false) Integer maxPrice
    ) {
        if (maxPrice == null) {
            maxPrice = Integer.MAX_VALUE;
        }
        return productService.findProductsByCostBetween(minPrice, maxPrice);
    }

    @PostMapping("/products")
    public Product saveNewProduct(@RequestBody Product product) {
        return productService.save(product);
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

    @PostMapping("/products/cost_between")
    public List<Product> findProductsByCostBetweenPost(@RequestParam(defaultValue = "-9999999999") Integer min, @RequestParam(defaultValue = "999999999") Integer max) {
        return productService.findProductsByCostBetween(min, max);
    }

}
