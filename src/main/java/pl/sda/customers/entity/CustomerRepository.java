package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // select * from customers where upper(first_name) like upper(?) and uppper(last_name) like upper(?)
    List<Person> findAllByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(String firstName, String lastName);
}
