package pl.sda.customers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomersApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
		assertNotNull(customerService);
		customerService.hello();
	}
}
