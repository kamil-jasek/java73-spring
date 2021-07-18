package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByStatus(OrderStatus status); // select o.* from orders o where o.status = ?
}
