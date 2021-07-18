package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // select * from customers where upper(first_name) like upper(?) and uppper(last_name) like upper(?)
    List<Person> findAllByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(String firstName, String lastName);

    List<Customer> findAllByEmailIgnoreCaseContains(String email);

    // select c.* from customers c inner join addresses a on a.customer_id = c.id where a.city = ?
    List<Customer> findAllByAddressesCityIgnoreCase(String city);

    // select c.* from customers c where (c.email = ? and c.status =?) or (c.type =? and c...)
}
