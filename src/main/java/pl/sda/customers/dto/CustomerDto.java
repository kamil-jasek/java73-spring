package pl.sda.customers.dto;

import java.util.Objects;
import java.util.UUID;

public final class CustomerDto {

    private final UUID id;
    private final String email;
    private final String name;

    public CustomerDto(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects
            .equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name);
    }
}
