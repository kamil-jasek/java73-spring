package pl.sda.customers.entity;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@EqualsAndHashCode
@ToString
public final class Product {

    @Id
    private UUID id;
    private String name;
    private double price;
    private int quantity;

    @OnlyForJpa
    private Product() {}

    public Product(String name, double price, int quantity) {
        state(price > 0, "price is equal or below zero");
        state(quantity > 0, "quantity is below 1");
        this.id = UUID.randomUUID();
        this.name = requireNonNull(name);
        this.price = price;
        this.quantity = quantity;
    }
}
