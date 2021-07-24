package pl.sda.customers.service;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.customers.dto.MakeOrderForm;
import pl.sda.customers.dto.OrderId;
import pl.sda.customers.entity.Order;
import pl.sda.customers.entity.OrderRepository;
import pl.sda.customers.entity.Product;
import pl.sda.customers.exception.CustomerNotExistsException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = requireNonNull(orderRepository);
    }

    @Transactional
    public OrderId makeOrder(MakeOrderForm form) {
        if (!(orderRepository.customerExists(form.getCustomerId()))) {
            throw new CustomerNotExistsException("customer not exists: " + form.getCustomerId());
        }
        final var order = new Order(form.getCustomerId(), mapProducts(form));
        orderRepository.save(order);
        return new OrderId(order.getId());
    }

    private List<Product> mapProducts(MakeOrderForm form) {
        return form.getProducts()
            .stream()
            .map(p -> new Product(p.getName(), p.getPrice(), p.getQuantity()))
            .collect(Collectors.toList());
    }
}
