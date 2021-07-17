package pl.sda.customers.entity;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Test
    void shouldSaveCustomerInRepository() {
        // given
        final var customer = new Company("abc@ab.pl", "Comp S.A.", "PL03030333");

        // when
        saveAndClearCache(customer);

        // then
        final var readCustomer = customerRepository.getById(customer.getId());
        assertEquals(readCustomer, customer);
    }

    @Test
    void shouldAddAddressToCustomer() {
        // given
        final var customer = new Company("abc@ab.pl", "Comp S.A.", "PL03030333");
        saveAndClearCache(customer);

        // when
        customer.addAddress(new Address("str", "Wawa", "02-333", "PL"));
        saveAndClearCache(customer);

        // then
        final var readCustomer = customerRepository.getById(customer.getId());
        assertEquals(customer.getAddresses(), readCustomer.getAddresses());
    }

    @Test
    void shouldSortCustomersByEmail() {
        // given
        final var customer1 = new Company("abc@ab.pl", "Test S.A.", "PL9393933");
        final var customer2 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        final var customer3 = new Company("ert@ab.pl", "ABC S.A.", "PL203020222");
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var sortedCustomers = customerRepository.findAll(Sort.by("email"));

        // then
        assertEquals(List.of(customer1, customer3, customer2), sortedCustomers);
    }

    private void saveAndClearCache(Customer ...customers) {
        customerRepository.saveAllAndFlush(asList(customers));
        em.clear();
    }
}