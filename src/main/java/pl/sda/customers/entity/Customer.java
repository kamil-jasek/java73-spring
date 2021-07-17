package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//@Entity
//@Table(name = "customers")
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {

    private UUID id;
    private String email;
    private List<Address> addresses;
}
