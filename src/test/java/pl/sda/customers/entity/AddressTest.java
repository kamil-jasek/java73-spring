package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddressTest extends EntityBaseTest {

    @Test
    void shouldSaveAddressInDatabase() {
        // given
        final var address = new Address("str", "Wawa", "01-520", "PL");

        // when
        persistAndClearCache(address);

        // then
        final var readAddress = em.find(Address.class, address.getId());
        assertEquals(readAddress, address);
    }
}