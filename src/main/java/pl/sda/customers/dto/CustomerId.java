package pl.sda.customers.dto;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

public final class CustomerId {

    private final UUID id;

    public CustomerId(UUID id) {
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
        CustomerId that = (CustomerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
