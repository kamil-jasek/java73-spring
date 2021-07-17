package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PersonTest {

    @Autowired
    private EntityManager em;

    @Transactional
    @Test
    void shouldSavePersonInDatabase() {
        // given
        final var person = new Person("abc@ab.pl", "Jan", "Kowalski", "PL93939933");

        // when
        em.persist(person);
        em.flush();
        em.clear();

        // then
        final var readPerson = em.find(Person.class, person.getId());
        assertEquals(readPerson, person);
    }
}