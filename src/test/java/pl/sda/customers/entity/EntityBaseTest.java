package pl.sda.customers.entity;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
abstract class EntityBaseTest {

    @Autowired
    protected EntityManager em;

    protected void persistAndClearCache(Object entity) {
        em.persist(entity);
        em.flush();
        em.clear();
    }
}
