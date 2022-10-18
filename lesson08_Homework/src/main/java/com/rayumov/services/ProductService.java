package com.rayumov.services;

import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional
    public void changeScore(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Unable to change products's score. Product not found, id: " + productId));
        product.setCost(product.getCost() + delta);
    }
    public List<Product> findProductsByCostBetween(Integer min, Integer max) {
        return  productRepository.findAllByCostBetween(min, max);
    }
    public List<Product> findProductsByCostAfter(Integer min) {
        return productRepository.findAllByCostAfter(min);
    }
    public List<Product> findProductByCostBefore(Integer max) {
        return productRepository.findAllByCostBefore(max);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
