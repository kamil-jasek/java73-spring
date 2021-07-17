package pl.sda.customers.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public final class Product {

    @Id
    private UUID id;
    private String name;
    private double price;
    private int quantity;

    @OnlyForJpa
    private Product() {}

    public Product(String name, double price, int quantity) {
        this.id = UUID.randomUUID();
        this.name = requireNonNull(name);
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && id.equals(product.id)
            && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity);
    }
}
