package springboot.repositories;


import org.springframework.stereotype.Component;
import springboot.data.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {

    private List<Product> products;

    @PostConstruct
   private void init() {
        products = new ArrayList<>(List.of(
               new Product(1L, "Orange", 300),
               new Product(2L, "Banana", 200),
               new Product(3L, "Apple", 100)
       ));
   }

   public Product getById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .get();
   }

   public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
   }

   public void deleteProduct(Long id) {
        products.removeIf(p -> p.getId().equals(id));
   }


}
