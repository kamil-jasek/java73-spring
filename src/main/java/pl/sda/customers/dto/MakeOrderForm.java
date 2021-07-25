package pl.sda.customers.dto;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.notEmpty;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class MakeOrderForm {

    private final UUID customerId;
    private final List<ProductDto> products;

    public MakeOrderForm(UUID customerId, List<ProductDto> products) {
        notEmpty(products, "products list is empty");
        this.customerId = requireNonNull(customerId);
        this.products = products;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public double getFullOrderPrice() {
        return products.stream()
            .mapToDouble(product -> product.getPrice() * product.getQuantity())
            .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MakeOrderForm that = (MakeOrderForm) o;
        return customerId.equals(that.customerId) && products.equals(that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, products);
    }
}
