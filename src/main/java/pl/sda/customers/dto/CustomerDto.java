package pl.sda.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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
}
