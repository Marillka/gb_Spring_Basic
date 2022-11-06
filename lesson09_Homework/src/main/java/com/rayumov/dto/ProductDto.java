package com.rayumov.dto;

import com.rayumov.entities.Product;

public class ProductDto {

    private Long id;
    private String name;
    private Integer cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.cost = product.getCost();
    }

    public ProductDto() {
    }
}
