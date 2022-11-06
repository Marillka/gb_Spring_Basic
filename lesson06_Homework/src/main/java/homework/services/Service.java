package homework.services;

import homework.dao.CustomerDaoImpl;
import homework.dao.OrderDaoImpl;
import homework.dao.ProductDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    private CustomerDaoImpl customerDaoImpl;
    private OrderDaoImpl orderDaoImpl;
    private ProductDaoImpl productDaoImpl;

    @Autowired
    public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
        this.customerDaoImpl = customerDaoImpl;
    }
    @Autowired
    public void setOrderDaoImpl(OrderDaoImpl orderDaoImpl) {
        this.orderDaoImpl = orderDaoImpl;
    }
    @Autowired
    public void setProductDaoImpl(ProductDaoImpl productDaoImpl) {
        this.productDaoImpl = productDaoImpl;
    }
}
