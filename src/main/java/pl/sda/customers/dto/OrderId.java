package pl.sda.customers.dto;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

public final class OrderId {

    private final UUID id;

    public OrderId(UUID id) {
        this.id = requireNonNull(id);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderId orderId = (OrderId) o;
        return id.equals(orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
