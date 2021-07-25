package pl.sda.customers.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.customers.dto.CustomerDto;
import pl.sda.customers.service.CustomerService;

@WebMvcTest(controllers = CustomerRestController.class)
class CustomerRestControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetListOfCustomers() throws Exception {
        // given
        when(customerService.listAllCustomers()).thenReturn(List.of(
            new CustomerDto(UUID.randomUUID(), "avc@wp.pl", "Comp S.A."),
            new CustomerDto(UUID.randomUUID(), "wer@wp.pl", "Jan Kowalski")
        ));

        // when
        mvc.perform(get("/api/customers"))
            .andDo(print())
        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[0].email", is("avc@wp.pl")))
            .andExpect(jsonPath("$[0].name", is("Comp S.A.")));
    }

    @Test
    void shouldGetSingleCustomer() {
        // Implement test
    }
}