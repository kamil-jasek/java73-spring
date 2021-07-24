package pl.sda.customers.dto;

import static java.util.Objects.requireNonNull;

public final class ProductDto {

    private final String name;
    private final double price;
    private final int quantity;

    public ProductDto(String name, double price, int quantity) {
        this.name = requireNonNull(name);
        this.price = price;
        this.quantity = quantity;
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
}
