package pl.sda.customers.entity;

import java.util.Arrays;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
abstract class RepositoryBaseTest<TYPE, ID> {

    @Autowired
    protected JpaRepository<TYPE, ID> repository;

    @Autowired
    protected EntityManager em;

    protected void saveAndClearCache(TYPE ...entities) {
        repository.saveAllAndFlush(Arrays.asList(entities));
        em.clear();
    }
}
