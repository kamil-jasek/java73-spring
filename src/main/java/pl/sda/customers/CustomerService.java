package pl.sda.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    // @Autowired - not recommended
    private final CustomerDao customerDao;
//    private SomeBean someBean;

//    @Autowired - optional
    CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

//    @Autowired
//    CustomerService(CustomerDao customerDao, SomeBean bean) {
//        this.customerDao = customerDao;
//        this.someBean = bean;
//    }

    public void hello() {
        System.out.println("hello service");
        customerDao.helloFromDao();
    }

//    @Autowired  - not recommended
//    public void setCustomerDao(CustomerDao customerDao) {
//        this.customerDao = customerDao;
//    }
}
