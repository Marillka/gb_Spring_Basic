package homework;

import org.hibernate.Session;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private SessionFactoryUtils sessionFactoryUtils;

    public ProductDaoImpl(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }

    @Override
    public Product findById(Long id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public List<Product> findAll() {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("from Product").getResultList();
            return products;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            // 1 вариант. Минус такого подхода в том, что здесь select. Второе - когда мы вытаскиваем из базы, вытаскивается не один экземпляр, а два, одним вы управляете, второй лежит в контексте. И получается  что мы тратим память.
//            Product product = session.get(Product.class, id);
//            session.delete(product);

            // 2 вариант через создание запроса
            session.createQuery("delete from Product where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdate(Product product) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }

    public void changePrice(Long id, Integer delta) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.createQuery("update Product p set price = :delta where p.id = :id")
                    .setParameter("id", id)
                    .setParameter("delta", delta + product.getPrice())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }



}
