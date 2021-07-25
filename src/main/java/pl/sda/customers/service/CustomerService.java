package pl.sda.customers.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.customers.dto.CustomerDto;
import pl.sda.customers.dto.CustomerId;
import pl.sda.customers.dto.RegisterCompanyForm;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.exception.EmailAlreadyExistsException;
import pl.sda.customers.exception.VatAlreadyExistsException;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Transactional
    public CustomerId registerCompany(RegisterCompanyForm form) { // POJO - Plain old java object
        if (repository.hasCustomerWithEmail(form.getEmail())) {
            throw new EmailAlreadyExistsException("email already exists: " + form.getEmail());
        }

        if (repository.hasCompanyWithVat(form.getVat())) {
            throw new VatAlreadyExistsException("vat already exists: " + form.getVat());
        }

        final var company = new Company(form.getEmail(), form.getName(), form.getVat());
        repository.save(company);
        return new CustomerId(company.getId());
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> listAllCustomers() {
        return repository.findAll().stream()
            .map(customer -> new CustomerDto(customer.getId(), customer.getEmail(), customer.getName()))
            .collect(toList());
    }
}
