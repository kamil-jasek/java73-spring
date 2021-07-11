package pl.sda.customers;

import org.junit.jupiter.api.Test;

public class CustomerServiceTest {

    @Test
    void testHello() {
        var customerService = new CustomerService(new CustomerDao());
//        customerService.setCustomerDao(new CustomerDao());
        customerService.hello();
    }
}
