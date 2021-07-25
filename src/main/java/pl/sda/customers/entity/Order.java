package pl.sda.customers.entity;

import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

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

    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Product> products;

    private double deliveryCost;

    @OnlyForJpa
    private Order() {
    }

    public Order(UUID customerId, List<Product> products) {
        notNull(customerId, "customer id is null");
        notEmpty(products, "product list is empty");
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.products = new ArrayList<>(products);
        this.status = OrderStatus.WAITING;
        this.deliveryCost = 0;
    }

    public Order(UUID customerId, List<Product> products, double deliveryCost) {
        this(customerId, products);
        state(deliveryCost >= 0, "delivery cost is below zero");
        this.deliveryCost = deliveryCost;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    void sent() {
        validateIsWaiting();
        status = OrderStatus.SENT;
    }

    void cancel() {
        validateIsWaiting();
        status = OrderStatus.CANCELED;
    }

    void markDelivered() {
        if (!status.equals(OrderStatus.SENT)) {
            throw new IllegalStateException("Invalid order status: " + status);
        }
        status = OrderStatus.DELIVERED;
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
        return id.equals(order.id) && customerId.equals(order.customerId) &&
            new HashSet<>(products).equals(new HashSet<>(order.products));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", customerId=" + customerId +
            ", status=" + status +
            '}';
    }
}
