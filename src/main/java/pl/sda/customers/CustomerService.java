package pl.sda.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void hello() {
        System.out.println("Hello");
    }
}
