package com.rayumov.controllers;

import com.rayumov.converters.ProductConverter;
import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.services.ProductService;
import com.rayumov.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    public Page<ProductDto> getProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(minCost, maxCost, titlePart, page).map(
                p -> productConverter.entityToDto(p)
        );
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @PostMapping()
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }






}
