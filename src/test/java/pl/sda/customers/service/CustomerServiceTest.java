package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.customers.dto.RegisterCompanyForm;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.exception.EmailAlreadyExistsException;
import pl.sda.customers.exception.VatAlreadyExistsException;

class CustomerServiceTest extends BaseServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldRegisterCompany() {
        // given
        final var form = new RegisterCompanyForm("abc@wp.pl", "PL93939393", "Compa S.A.");

        // when
        final var customerId = customerService.registerCompany(form);

        // then
        assertNotNull(customerId);
        assertTrue(repository.existsById(customerId.getId()));
    }

    @Test
    void shouldNotRegisterCompanyIfEmailAlreadyExists() {
        // given
        persistAndClearCache(new Company("abc@wp.pl", "Compa S.A.", "PL939230203"));
        final var form = new RegisterCompanyForm("abc@wp.pl", "PL02030303", "Test S.A.");

        // then
        assertThrows(EmailAlreadyExistsException.class, () -> customerService.registerCompany(form));
    }

    @Test
    void shouldNotRegisterCompanyIfVatAlreadyExists() {
        // given
        persistAndClearCache(new Company("bca@wp.pl", "Comp S.A.", "PL98393993"));
        final var form = new RegisterCompanyForm("abc@wp.pl", "PL98393993", "Test S.A.");

        // then
        assertThrows(VatAlreadyExistsException.class, () -> customerService.registerCompany(form));
    }
}