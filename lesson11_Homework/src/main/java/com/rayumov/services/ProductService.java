package com.rayumov.services;

import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.repositories.ProductRepository;
import com.rayumov.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Page<Product> findAll(Integer minCost, Integer maxCost, String titlePart, Integer page) {
        Specification<Product> specification = Specification.where(null);
        if (minCost != null) {
            specification = specification.and(ProductSpecifications.costGreaterOrEqualsThan(minCost));
        }
        if (maxCost != null) {
            specification = specification.and(ProductSpecifications.costLessOrEqualsThen(maxCost));
        }
        if (titlePart != null) {
            specification = specification.and(ProductSpecifications.nameLike(titlePart));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 60));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Немозможно обновить продукт, не найден в базе, id " + productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        return product;
    }



}
