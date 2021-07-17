package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Test
    void shouldSaveOrder() {
        // given
        final var order = new Order(List.of(new Product("test", 12., 1)));

        // when
        orderRepository.saveAndFlush(order);
        em.clear();

        // then
        final var readOrder = orderRepository.getById(order.getId());
        assertEquals(readOrder, order);
    }
}