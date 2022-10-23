package com.rayumov.specifications;

import com.rayumov.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> costGreaterOrEqualsThan(Integer cost) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost);
    }

    public static Specification<Product> costLessOrEqualsThen(Integer cost) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), cost);
    }

    public static Specification<Product> nameLike(String titlePart) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart)));
    }

    public static Specification<Product> inCart(Boolean inCart) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isInCart")));
    }
}
