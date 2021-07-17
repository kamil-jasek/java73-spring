package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AddressTest {

    @Autowired
    private EntityManager em;

    @Transactional
    @Test
    void shouldSaveAddressInDatabase() {
        // given
        final var address = new Address("str", "Wawa", "01-500", "PL");

        // when
        em.persist(address);
        em.flush();
        em.clear();

        // then
        final var readAddress = em.find(Address.class, address.getId());
        assertEquals(readAddress, address);
    }
}