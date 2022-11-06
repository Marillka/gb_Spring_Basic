package homework.controllers;

import homework.data.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import homework.repositories.ProductRepository;

@Controller
public class ProductController {


    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("products")
    public String showProductsPage(Model model) {
        model.addAttribute("allProducts", productRepository.getAllProducts());
        return "products_page";
    }

    @GetMapping("products/{id}")
    public String showProductPage(Model model, @PathVariable Long id) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "product_info_page";
    }
}

