package pl.sda.customers.dto;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public final class RegisterCompanyForm {

    private final String email;
    private final String vat;
    private final String name;

    public RegisterCompanyForm(String email, String vat, String name) {
        this.email = requireNonNull(email);
        this.vat = requireNonNull(vat);
        this.name = requireNonNull(name);
    }

    public String getEmail() {
        return email;
    }

    public String getVat() {
        return vat;
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
        RegisterCompanyForm that = (RegisterCompanyForm) o;
        return email.equals(that.email) && vat.equals(that.vat) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, vat, name);
    }
}
