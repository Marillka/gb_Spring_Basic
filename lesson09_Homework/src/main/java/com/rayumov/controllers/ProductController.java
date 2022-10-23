package com.rayumov.controllers;

import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import com.rayumov.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDto> getProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "max_cost", required = false) Integer minCost,
            @RequestParam(name = "min_cost", required = false) Integer maxCost,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minCost, maxCost, namePart, page).map(
                s -> new ProductDto(s)
        );
    }

    @GetMapping("/{id}")
    public Optional<ProductDto> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        Product product = optionalProduct.get();
        Optional<ProductDto> ProductDto = Optional.of(new ProductDto(product));
        return ProductDto;
    }

    @PostMapping()
    public ProductDto saveNewProduct(@RequestBody ProductDto product) {
        product.setId(null);
        Product newProduct = productService.save(new Product(product));
        return new ProductDto(newProduct);

    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        Product updatedProduct = productService.save(new Product(product));
        return new ProductDto(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
