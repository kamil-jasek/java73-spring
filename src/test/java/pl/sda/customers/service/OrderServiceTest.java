package pl.sda.customers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.customers.dto.MakeOrderForm;
import pl.sda.customers.dto.ProductDto;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.OrderRepository;
import pl.sda.customers.exception.CustomerNotExistsException;

public class OrderServiceTest extends BaseServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldMakeOrderWithDefaultDeliveryCost() {
        // given
        final var company = new Company("bca@wp.pl", "Comp S.A.", "PL98393993");
        persistAndClearCache(company);
        final var form = new MakeOrderForm(company.getId(), List.of(new ProductDto("xyz", 2, 1)));

        // when
        final var orderId = orderService.makeOrder(form);

        // then
        assertNotNull(orderId);
        final var createdOrder = orderRepository.getById(orderId.getId());
        assertEquals(15., createdOrder.getDeliveryCost());
    }

    @Test
    void shouldMakeOrderWithFreeDelivery() {
        // given
        final var company = new Company("bca@wp.pl", "Comp S.A.", "PL98393993");
        persistAndClearCache(company);
        final var form = new MakeOrderForm(company.getId(), List.of(new ProductDto("xyz", 251, 1)));

        // when
        final var orderId = orderService.makeOrder(form);

        // then
        assertNotNull(orderId);
        final var createdOrder = orderRepository.getById(orderId.getId());
        assertEquals(0., createdOrder.getDeliveryCost());
    }

    @Test
    void shouldNotMakeOrderCustomerNotExists() {
        // given
        final var form = new MakeOrderForm(UUID.randomUUID(), List.of(new ProductDto("xyz", 2, 1)));

        // then
        assertThrows(CustomerNotExistsException.class, () -> orderService.makeOrder(form));
    }
}
