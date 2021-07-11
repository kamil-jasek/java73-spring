package pl.sda.customers;

import org.springframework.stereotype.Component;

@Component
public class CustomerDao {

    public String helloFromDao() {
        return "test";
    }
}
