package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
        final var customer2 = new Person("xyz@ba.pl", "Janek", "Kowal", "9203023020");
        final var customer3 = new Person("xyz@cb.pl", "Janisław", "Nowak", "9203023020");
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var customers = repository.findAllByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike("jan%", "kowal%");

        // then
        assertEquals(List.of(customer1, customer2), customers);
    }

    @Test
    void shouldFilterByPersonName() {
        // given
        final var customer1 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        final var customer2 = new Person("xyz@ba.pl", "Janek", "Kowal", "9203023020");
        final var customer3 = new Person("xyz@cb.pl", "Janisław", "Nowak", "9203023020");
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var customers = repository.filterByPersonName("jan%", "kowal%");

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

    @Test
    void shouldFindCompaniesInCountry() {
        // given
        final var customer1 = new Company("abc@ab.pl", "Test S.A.", "PL9393933");
        customer1.addAddress(new Address("str", "Warszawa", "01-200", "PL"));
        final var customer2 = new Company("xyz@ab.pl", "Kowalski S.A.", "9203023020");
        customer2.addAddress(new Address("str", "Kraków", "03-400", "PL"));
        final var customer3 = new Company("ert@ab.pl", "ABC S.A.", "PL203020222");
        customer3.addAddress(new Address("str", "Berlin", "01-300", "DE"));
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var companies = repository.findCompaniesInCountry("%test%", "PL");

        // then
        assertEquals(List.of(customer1), companies);
    }

    @Test
    void shouldFindStreetsForLastName() {
        // given
        final var customer1 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        customer1.addAddress(new Address("Swietokrzyska", "Warszawa", "01-200", "PL"));
        final var customer2 = new Person("xyz@cb.pl", "Janek", "Kowal", "9203023020");
        customer2.addAddress(new Address("Krakowska", "Kraków", "03-400", "PL"));
        customer2.addAddress(new Address("Swietokrzyska", "Warszawa", "01-200", "PL"));
        final var customer3 = new Person("xyz@as.pl", "Janisław", "Nowak", "9203023020");
        customer3.addAddress(new Address("Strase", "Berlin", "01-300", "DE"));
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var streets = repository.findStreetsForLastName("kowal%");

        // then
        assertEquals(List.of("Krakowska", "Swietokrzyska"), streets);
    }

    @Test
    void shouldFindPersonNameByCountry() {
        // given
        final var customer1 = new Person("xyz@ab.pl", "Jan", "Kowalski", "9203023020");
        customer1.addAddress(new Address("Swietokrzyska", "Warszawa", "01-200", "PL"));
        final var customer2 = new Person("xyz@cb.pl", "Janek", "Tadzik", "9203023020");
        customer2.addAddress(new Address("Krakowska", "Kraków", "03-400", "PL"));
        customer2.addAddress(new Address("Swietokrzyska", "Warszawa", "01-200", "PL"));
        final var customer3 = new Person("xyz@ka.pl", "Janisław", "Nowak", "9203023020");
        customer3.addAddress(new Address("Strase", "Berlin", "01-300", "DE"));
        saveAndClearCache(customer1, customer2, customer3);

        // when
        final var personNames = repository.findPersonNameByCountry("PL");

        // then
        assertEquals(3, personNames.size());
        assertArrayEquals(new Object[] { "Jan", "Kowalski", "Warszawa" }, personNames.get(0));
        assertArrayEquals(new Object[] { "Janek", "Tadzik", "Kraków" }, personNames.get(1));
        assertArrayEquals(new Object[] { "Janek", "Tadzik", "Warszawa" }, personNames.get(2));
    }
}