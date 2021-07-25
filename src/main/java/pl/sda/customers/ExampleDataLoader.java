package pl.sda.customers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;

@Component
@Profile("dev")
class ExampleDataLoader {

    private final CustomerRepository repository;

    ExampleDataLoader(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @EventListener
    public void onApplicationLoad(ApplicationReadyEvent event) {
        repository.save(new Company("abc@wp.pl", "Comp S.A.", "PL838399393"));
        repository.save(new Person("awe@qw.pl", "Jan", "Kowalski", "9039389383"));
    }
}
