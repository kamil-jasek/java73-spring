package pl.sda.customers.service;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
abstract class BaseServiceTest {

    @Autowired
    protected EntityManager em;

    protected void persistAndClearCache(Object entity) {
        em.persist(entity);
        em.flush();
        em.clear();
    }
}
