package homework;

import homework.config.AppConfig;
import homework.dao.CustomerDaoImpl;
import homework.dao.OrderDaoImpl;
import homework.dao.ProductDaoImpl;
import homework.db.SessionFactoryUtils;
import homework.services.Service;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var factory = context.getBean(SessionFactoryUtils.class);




        var customerDaoImpl = context.getBean(homework.dao.CustomerDaoImpl.class);
        var orderDaoImpl = context.getBean(homework.dao.OrderDaoImpl.class);
        var productDaoImpl = context.getBean(homework.dao.ProductDaoImpl.class);

        try {
            System.out.println(productDaoImpl.findById(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
