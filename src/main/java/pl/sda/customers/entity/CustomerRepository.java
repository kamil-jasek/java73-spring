package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.Customer;
import pl.sda.customers.entity.Person;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // select * from customers where upper(first_name) like upper(?) and uppper(last_name) like upper(?)
    List<Person> findAllByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(String firstName, String lastName);

    @Query("from Person p where upper(p.firstName) like upper(?1) and upper(p.lastName) like upper(?2)")
    List<Person> filterByPersonName(String firstName, String lastName);

    List<Customer> findAllByEmailIgnoreCaseContains(String email); // -> contains -> like %?1%

    // select c.* from customers c inner join addresses a on a.customer_id = c.id where a.city = ?
    List<Customer> findAllByAddressesCityIgnoreCase(String city);

    // select c.* from customers c where (c.email = ? and c.status =?) or (c.type =? and c...)

    @Query("from Company c join c.addresses a where upper(c.name) like upper(?1) and a.country = ?2")
    List<Company> findCompaniesInCountry(String partOfName, String country);

    @Query("select distinct a.street "
        + "from Person p join p.addresses a "
        + "where upper(p.lastName) like upper(?1) "
        + "order by a.street")
    List<String> findStreetsForLastName(String lastName);
    
    @Query(value = "select p.first_name, p.last_name, a.city "
        + "from customers p "
        + "inner join customer_addresses a on a.customer_id = p.id "
        + "where p.customer_type = 'PERSON' and a.country = ?1 order by p.last_name, a.city",
        nativeQuery = true)
    List<Object[]> findPersonNameByCountry(String country);

    @Query("select (count(c.id) > 0) from Customer c where upper(c.email) = upper(?1)")
    boolean hasCustomerWithEmail(String email);

    @Query("select (count(c.id) > 0) from Company c where upper(c.vat) = upper(?1)")
    boolean hasCompanyWithVat(String vat);
}
