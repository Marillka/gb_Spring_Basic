package homework;

/*
Задание
1. Создайте сущность homework.Product (Long id, String title, int price) и таблицу в базе данных для хранения объектов этой сущности;
2. Создайте класс homework.ProductDao и реализуйте в нем логику выполнения CRUD-операций над сущностью homework.Product (homework.Product findById(Long id), List<homework.Product> findAll(), void deleteById(Long id), homework.Product saveOrUpdate(homework.Product product));
3. * Вшить homework.ProductDao в веб-проект, и показывать товары, лежащие в базе данных. Помните что в таком случае SessionFactory или обертку над ней надо будет делать в виде Spring бина.
 */
public class MainApp {
    public static void main(String[] args) {
        SessionFactoryUtils sessionFactoryUtils = new SessionFactoryUtils();
        sessionFactoryUtils.init();

        try {
            ProductDao productDao = new ProductDaoImpl(sessionFactoryUtils);

            System.out.println(productDao.findAll());

            productDao.saveOrUpdate(new Product("Milk", 100));
            System.out.println(productDao.findAll());


            productDao.deleteById(2L);

            System.out.println(productDao.findAll());

            productDao.changePrice(1L, 80);


            System.out.println(productDao.findById(1L));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactoryUtils.shutdown();
        }
    }
}
