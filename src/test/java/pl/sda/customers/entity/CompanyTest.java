package pl.sda.customers.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompanyTest extends EntityBaseTest {

    @Test
    void shouldSaveCompanyInDatabase() {
        // given
        final var company = new Company("abc@ab.pl", "Compa S.A.", "PL93992092");

        // when
        persistAndClearCache(company);

        // then
        final var readCompany = em.find(Company.class, company.getId());
        assertEquals(readCompany, company);
    }
}
