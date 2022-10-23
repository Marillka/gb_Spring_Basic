package com.rayumov.converters;

import com.rayumov.dto.ProductDto;
import com.rayumov.entities.Product;
import org.springframework.stereotype.Component;

// класс - преобразователь
@Component
public class ProductConverter {

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

}
