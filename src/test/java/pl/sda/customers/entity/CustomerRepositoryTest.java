package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class CustomerRepositoryTest extends RepositoryBaseTest<CustomerRepository> {

    @Test
    void shouldSaveCustomerInRepository() {
        // given
        final var customer = new Company("abc@ab.pl", "Comp S.A.", "PL03030333");

        // when
        saveAndClearCache(customer);

        // then
        final var readCustomer = repository.getById(customer.getId());
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
        final var readCustomer = repository.getById(customer.getId());
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
        final var sortedCustomers = repository.findAll(Sort.by("email"));

        // then
        assertEquals(List.of(customer1, customer3, customer2), sortedCustomers);
    }

    @Test
    void shouldFindPersonByFirstAndLastName() {
        // given
        final var customer1 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        final var customer2 = new Person("xyz@ab.pl", "Janek", "Kowal", "9203023020");
        final var customer3 = new Person("xyz@ab.pl", "Janisław", "Nowak", "9203023020");
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var customers = repository.findAllByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike("jan%", "kowal%");

        // then
        assertEquals(List.of(customer1, customer2), customers);
    }

    @Test
    void shouldFindCustomersByContainingTextInEmail() {
        // given
        final var customer1 = new Company("abcd@ab.pl", "Test S.A.", "PL9393933");
        final var customer2 = new Person("bcd@ab.pl", "Jan", "Kowalski", "9203023020");
        final var customer3 = new Company("cda@qw.pl", "ABC S.A.", "PL203020222");
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var customers = repository.findAllByEmailIgnoreCaseContains("AB.pl");

        // then
        assertEquals(List.of(customer1, customer2), customers);
    }

    @Test
    void shouldFindCustomersFromCity() {
        // given
        final var customer1 = new Company("abc@ab.pl", "Test S.A.", "PL9393933");
        customer1.addAddress(new Address("str", "Warszawa", "01-200", "PL"));
        final var customer2 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        customer2.addAddress(new Address("str", "Kraków", "03-400", "PL"));
        final var customer3 = new Company("ert@ab.pl", "ABC S.A.", "PL203020222");
        customer3.addAddress(new Address("str", "Warszawa", "01-300", "PL"));
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var customers = repository.findAllByAddressesCityIgnoreCase("warszawa");

        // then
        assertEquals(List.of(customer1, customer3), customers);
    }
}