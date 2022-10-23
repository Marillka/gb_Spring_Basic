package com.rayumov.controllers;

import com.rayumov.converters.ProductConverter;
import com.rayumov.dto.ProductDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
@RequiredArgsConstructor
public class Cart {
    private ArrayList<ProductDto> productsList = new ArrayList<>();

    private final ProductConverter productConverter;

    public ArrayList<ProductDto> getProductsInCart() {
        return productsList;
    }

    public ProductDto addProduct(ProductDto productDto) {
         productsList.add(productDto);
         return productDto;
    }

    public ProductDto deleteProduct(ProductDto productDto) {
        productsList.remove(productDto);
        return productDto;
    }


}
