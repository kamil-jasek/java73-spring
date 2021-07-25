package pl.sda.customers.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.customers.dto.MakeOrderForm;
import pl.sda.customers.dto.OrderId;
import pl.sda.customers.dto.ProductDto;
import pl.sda.customers.entity.Order;
import pl.sda.customers.entity.OrderRepository;
import pl.sda.customers.entity.Product;
import pl.sda.customers.exception.CustomerNotExistsException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final double defaultDeliveryCost;

    public OrderService(OrderRepository orderRepository,
        @Value("${order.default.delivery-cost}") double defaultDeliveryCost) {
        this.orderRepository = requireNonNull(orderRepository);
        this.defaultDeliveryCost = defaultDeliveryCost;
    }

    @Transactional
    public OrderId makeOrder(MakeOrderForm form) {
        if (!(orderRepository.customerExists(form.getCustomerId()))) {
            throw new CustomerNotExistsException("customer not exists: " + form.getCustomerId());
        }

        double deliveryCost = this.defaultDeliveryCost;
        if (form.getFullOrderPrice() > 250.) {
            deliveryCost = 0.;
        }

        final var order = new Order(form.getCustomerId(), mapProducts(form.getProducts()), deliveryCost);
        orderRepository.save(order);
        return new OrderId(order.getId());
    }

    private List<Product> mapProducts(List<ProductDto> products) {
        return products.stream()
            .map(p -> new Product(p.getName(), p.getPrice(), p.getQuantity()))
            .collect(toList());
    }
}
