package pl.sda.customers.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COMPANY")
public final class Company extends Customer {

    private String name;

    @Column(unique = true)
    private String vat;

    @OnlyForJpa
    protected Company() {
    }

    public Company(String email, String name, String vat) {
        super(email);
        this.name = requireNonNull(name);
        this.vat = requireNonNull(vat);
    }

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    @Override
    public String getTaxId() {
        return vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Company company = (Company) o;
        return name.equals(company.name) && vat.equals(company.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vat);
    }
}
