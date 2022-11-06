package homework;

import java.util.List;

public interface ProductDao {

    Product findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    void saveOrUpdate(Product product);

     void changePrice(Long id, Integer delta);

}
