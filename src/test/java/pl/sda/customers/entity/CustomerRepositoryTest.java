package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private void saveAndClearCache(Company customer) {
        customerRepository.saveAndFlush(customer);
        em.clear();
    }
}