package pl.sda.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByStatus(OrderStatus status); // select o.* from orders o where o.status = ?

    @Query("from Order o join fetch o.products where o.status in ('PAID', 'DELIVERED', 'SENT')")
    List<Order> fetchOrdersInProcessing();

    @Query("select count(o) from Order o where o.status = 'DELIVERED'")
    int countOrdersInDeliveredStatus();

    @Query("select sum(p.price * p.quantity) from Order o join o.products p where o.status = 'WAITING'")
    double sumOrdersValueInWaitingStatus();
}
