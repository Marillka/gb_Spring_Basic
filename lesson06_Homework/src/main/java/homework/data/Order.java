package homework.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer productCost;

    public Order(Long id, Customer customer, Product product, Integer productCost) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.productCost = productCost;
    }

    public Order(Customer customer, Product product, Integer productCost) {
        this.customer = customer;
        this.product = product;
        this.productCost = productCost;
    }

}
