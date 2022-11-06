package com.rayumov.controllers;

import com.rayumov.converters.ProductConverter;
import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import com.rayumov.exceptions.ResourceNotFoundException;
import com.rayumov.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
//@RequestMapping("/api/v1/cart")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final Cart cart;
    private final ProductConverter productConverter;

    @GetMapping
    public ArrayList<ProductDto> getCartList() {
        return cart.getProductsInCart();
    }

    @PostMapping
    public ProductDto addProductToCart(
            @RequestParam(value = "productId") Long id
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        return cart.addProduct(productDto);
    }

    @GetMapping("/delete/{id}")
    public ProductDto deleteProductFromCart(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        return cart.deleteProduct(productDto);
    }


}
