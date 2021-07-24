package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.customers.dto.RegisterCompanyForm;
import pl.sda.customers.entity.CustomerRepository;

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
}