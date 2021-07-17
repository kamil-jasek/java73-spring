package pl.sda.customers.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_addresses")
public final class Address {

    @Id
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    @OnlyForJpa
    private Address() {}

    public Address(String street, String city, String zipCode, String country) {
        this.id = UUID.randomUUID();
        this.street = requireNonNull(street);
        this.city = requireNonNull(city);
        this.zipCode = requireNonNull(zipCode);
        this.country = requireNonNull(country);
    }

    public UUID getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return id.equals(address.id) && street.equals(address.street) && city.equals(address.city) && zipCode
            .equals(address.zipCode) && country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode, country);
    }
}
