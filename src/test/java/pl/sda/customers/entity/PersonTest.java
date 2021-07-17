package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PersonTest extends EntityBaseTest {

    @Test
    void shouldSavePersonInDatabase() {
        // given
        final var person = new Person("abc@ab.pl", "Jan", "Kowalski", "PL93939933");

        // when
        persistAndClearCache(person);

        // then
        final var readPerson = em.find(Person.class, person.getId());
        assertEquals(readPerson, person);
    }
}