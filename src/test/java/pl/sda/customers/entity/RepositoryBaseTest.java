package pl.sda.customers.entity;

import java.util.Arrays;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
abstract class RepositoryBaseTest<REPOSITORY extends JpaRepository> {

    @Autowired
    protected REPOSITORY repository;

    @Autowired
    protected EntityManager em;

    protected void saveAndClearCache(Object ...entities) {
        repository.saveAllAndFlush(Arrays.asList(entities));
        em.clear();
    }
}
