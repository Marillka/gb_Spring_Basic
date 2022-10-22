package com.rayumov.services;

import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import com.rayumov.repositories.ProductRepository;
import com.rayumov.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> find(Integer minCost, Integer maxCost, String namePart, Integer page) {
        Specification<Product> specification = Specification.where(null);
        if (minCost != null) {
            specification = specification.and(ProductSpecifications.costGreaterOrEqualsThan(minCost));
        }
        if (maxCost != null) {
            specification = specification.and(ProductSpecifications.costLessOrEqualsThen(maxCost));
        }
        if (namePart != null) {
            specification = specification.and(ProductSpecifications.nameLike(namePart));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 10));
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(
                s -> new ProductDto(s)
        );
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto save(Product product) {
        Product newProduct = productRepository.save(product);
        return new ProductDto(newProduct);
    }
}
