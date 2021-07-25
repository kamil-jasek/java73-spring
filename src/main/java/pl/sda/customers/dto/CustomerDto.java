package pl.sda.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
public final class CustomerDto {

    private final UUID id;
    private final String email;
    private final String name;
    private String taxId;
    private List<AddressDto> addresses;

    public CustomerDto(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public CustomerDto(UUID id, String email, String name, String taxId,
        List<AddressDto> addresses) {
        this(id, email, name);
        this.taxId = taxId;
        this.addresses = addresses;
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

    public String getTaxId() {
        return taxId;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
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
