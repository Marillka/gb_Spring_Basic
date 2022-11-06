package springboot.services;


import org.springframework.stereotype.Service;
import springboot.data.Product;
import springboot.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    public void changeCost(Long id, Integer delta) {
        Product product = productRepository.getById(id);
        product.setCost(product.getCost() + delta);
    }
}
