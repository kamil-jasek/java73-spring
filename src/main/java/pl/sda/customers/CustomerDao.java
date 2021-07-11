package pl.sda.customers;

import org.springframework.stereotype.Component;

@Component
public class CustomerDao {

    public void helloFromDao() {
        System.out.println("hello from dao");
    }
}
