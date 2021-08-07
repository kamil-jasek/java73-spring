package pl.sda.customers.rest;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.dto.CustomerDto;
import pl.sda.customers.dto.CustomerId;
import pl.sda.customers.dto.RegisterCompanyForm;
import pl.sda.customers.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
class CustomerRestController {

    private final CustomerService customerService;

    CustomerRestController(CustomerService customerService) {
        this.customerService = requireNonNull(customerService);
    }

    @PreAuthorize("hasRole('CUSTOMER_READ')")
    @GetMapping
    ResponseEntity<List<CustomerDto>> getListOfCustomers() { // List<CustomerDto> --> JSON
        return ResponseEntity.ok(customerService.listAllCustomers()); // --> status code 200 (ok)
    }

    @PreAuthorize("hasRole('CUSTOMER_READ')")
    @GetMapping("/{customerId}")
    ResponseEntity<CustomerDto> findById(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @PreAuthorize("hasRole('CUSTOMER_WRITE')")
    @PostMapping
    ResponseEntity<CustomerId> registerCompany(@RequestBody RegisterCompanyForm form) {
        final var customerId = customerService.registerCompany(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }
}
