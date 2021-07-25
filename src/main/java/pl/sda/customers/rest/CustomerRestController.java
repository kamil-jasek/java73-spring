package pl.sda.customers.rest;

import static java.util.Objects.requireNonNull;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.dto.CustomerDto;
import pl.sda.customers.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
final class CustomerRestController {

    private final CustomerService customerService;

    CustomerRestController(CustomerService customerService) {
        this.customerService = requireNonNull(customerService);
    }

    @GetMapping
    ResponseEntity<List<CustomerDto>> getListOfCustomers() { // List<CustomerDto> --> JSON
        return ResponseEntity.ok(customerService.listAllCustomers()); // --> status code 200 (ok)
    }

    void registerCompany() {

    }
}
