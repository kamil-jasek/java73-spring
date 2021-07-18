package pl.sda.customers.entity;

import static org.springframework.util.Assert.notEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public final class Order {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Product> products;

    @OnlyForJpa
    private Order() {
    }

    public Order(List<Product> products) {
        notEmpty(products, "product list is empty");
        this.id = UUID.randomUUID();
        this.products = new ArrayList<>(products);
        this.status = OrderStatus.WAITING;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    void sent() {
        validateIsWaiting();
        status = OrderStatus.SENT;
    }

    void cancel() {
        validateIsWaiting();
        status = OrderStatus.CANCELED;
    }

    private void validateIsWaiting() {
        if (!status.equals(OrderStatus.WAITING)) {
            throw new IllegalStateException("Invalid order status");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id.equals(order.id) && new HashSet<>(products).equals(new HashSet<>(order.products));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", status=" + status +
            ", products=" + products +
            '}';
    }
}
