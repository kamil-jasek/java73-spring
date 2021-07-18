package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderRepositoryTest extends RepositoryBaseTest<Order, UUID> {

    @Test
    void shouldSaveOrder() {
        // given
        final var order = new Order(List.of(new Product("test", 12., 1)));

        // when
        saveAndClearCache(order);

        // then
        final var readOrder = repository.getById(order.getId());
        assertEquals(readOrder, order);
    }

    @Test
    void shouldSortOrdersByStatus() {
        // given

    }
}