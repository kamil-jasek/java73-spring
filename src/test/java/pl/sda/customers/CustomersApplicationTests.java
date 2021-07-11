package pl.sda.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomersApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
		customerService.hello();
	}

}
