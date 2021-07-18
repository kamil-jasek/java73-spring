package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class OrderRepositoryTest extends RepositoryBaseTest<OrderRepository> {

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
        final var order1 = new Order(List.of(new Product("abc", 1., 1)));
        final var order2 = new Order(List.of(new Product("bca", 2., 1)));
        final var order3 = new Order(List.of(new Product("cda", 3., 1)));
        order2.sent();
        order3.cancel();
        saveAndClearCache(order1, order2, order3);

        // when
        final var orders = repository.findAll(Sort.by("status"));

        // then
        assertEquals(List.of(order3, order2, order1), orders);
    }

    @Test
    void shouldFindOrdersByStatus() {
        // given
        final var order1 = new Order(List.of(new Product("abc", 1., 1)));
        final var order2 = new Order(List.of(new Product("bca", 2., 1)));
        final var order3 = new Order(List.of(new Product("cda", 3., 1)));
        order2.sent();
        order3.sent();
        saveAndClearCache(order1, order2, order3);

        // when
        final var orders = repository.findAllByStatus(OrderStatus.SENT);

        // then
        assertEquals(List.of(order2, order3), orders);
    }

    @Test
    void shouldFindOrdersInProcessing() {
        // given
        final var order1 = new Order(List.of(new Product("abc", 1., 1)));
        final var order2 = new Order(List.of(new Product("bca", 2., 1)));
        final var order3 = new Order(List.of(new Product("cda", 3., 1)));
        order2.sent();
        order3.sent();
        saveAndClearCache(order1, order2, order3);

        // when
        final var orders = repository.fetchOrdersInProcessing();

        // then
        assertEquals(List.of(order2, order3), orders);
    }
}