package homework.dao;

import homework.data.Customer;
import homework.db.SessionFactoryUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDaoImpl {
    private SessionFactoryUtils sessionFactoryUtils;

    public Customer findById(Long id) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            session.getTransaction().commit();
            return customer;
        }
    }


    public List<Customer> findAll() {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            List<Customer> customers = session.createQuery("from Customer").getResultList();
            session.getTransaction().commit();
            return customers;
        }
    }

    public void saveOrUpdate(Customer customer) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(customer);
            session.getTransaction().commit();
        }
    }

    public SessionFactoryUtils getSessionFactoryUtils() {
        return sessionFactoryUtils;
    }

    @Autowired
    public void setSessionFactoryUtils(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }






}
