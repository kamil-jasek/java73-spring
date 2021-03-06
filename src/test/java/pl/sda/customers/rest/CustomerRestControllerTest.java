package pl.sda.customers.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.customers.dto.AddressDto;
import pl.sda.customers.dto.CustomerDto;
import pl.sda.customers.dto.CustomerId;
import pl.sda.customers.exception.EmailAlreadyExistsException;
import pl.sda.customers.service.CustomerService;

@WebMvcTest(controllers = CustomerRestController.class)
class CustomerRestControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mvc;

    private final String testBasicAuthToken = "Basic dGVzdDp0ZXN0";
    private final String adminBasicAuthToken = "Basic YWRtaW46YWRtaW4=";

    @Test
    void shouldGetListOfCustomers() throws Exception {
        // given
        when(customerService.listAllCustomers()).thenReturn(List.of(
            new CustomerDto(UUID.randomUUID(), "avc@wp.pl", "Comp S.A."),
            new CustomerDto(UUID.randomUUID(), "wer@wp.pl", "Jan Kowalski")
        ));

        // when
        mvc.perform(get("/api/customers")
                .header(AUTHORIZATION, testBasicAuthToken))
            .andDo(print())
        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[0].email", is("avc@wp.pl")))
            .andExpect(jsonPath("$[0].name", is("Comp S.A.")));
    }

    @Test
    void shouldGetSingleCustomer() throws Exception {
        // given
        final var customerId = UUID.fromString("7c29e663-11ff-4975-8136-b09be1f5d379");
        when(customerService.findById(customerId))
            .thenReturn(new CustomerDto(customerId,
                "abc@wp.pl",
                "Comp S.A.",
                "PL9393933",
                List.of(new AddressDto("str", "Wawa", "22-200", "PL"))));

        // when
        mvc.perform(get("/api/customers/" + customerId)
                .header(AUTHORIZATION, testBasicAuthToken))
            .andDo(print())
        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(customerId.toString())))
            .andExpect(jsonPath("$.email", is("abc@wp.pl")))
            .andExpect(jsonPath("$.name", is("Comp S.A.")))
            .andExpect(jsonPath("$.taxId", is("PL9393933")))
            .andExpect(jsonPath("$.addresses.length()", is(1)))
            .andExpect(jsonPath("$.addresses[0].street", is("str")))
            .andExpect(jsonPath("$.addresses[0].city", is("Wawa")))
            .andExpect(jsonPath("$.addresses[0].zipCode", is("22-200")))
            .andExpect(jsonPath("$.addresses[0].country", is("PL")));
    }

    @Test
    void shouldGet404WhenCustomerNotExists() throws Exception {
        // given
        when(customerService.findById(any()))
            .thenThrow(new EntityNotFoundException());

        // when
        mvc.perform(get("/api/customers/8111289b-33be-4729-bb90-e378b1d48033")
                .header(AUTHORIZATION, testBasicAuthToken))
        // then
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldRegisterCompany() throws Exception {
        // given
        final var customerId = "cb3d7313-dd5d-4dd2-87c6-e04845d5fe6a";
        when(customerService.registerCompany(any())).thenReturn(new CustomerId(UUID.fromString(customerId)));

        // when
        mvc.perform(post("/api/customers")
            .header(AUTHORIZATION, adminBasicAuthToken)
            .content(registerCompanyJson())
            .contentType(MediaType.APPLICATION_JSON))
        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(customerId)));
    }

    @Test
    void shouldInformAboutExistingEmailWhenRegisteringCompany() throws Exception {
        // given
        when(customerService.registerCompany(any()))
            .thenThrow(new EmailAlreadyExistsException("email already exists: abc@wp.pl"));

        // when
        mvc.perform(post("/api/customers")
            .header(AUTHORIZATION, adminBasicAuthToken)
            .content(registerCompanyJson())
            .contentType(MediaType.APPLICATION_JSON))
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("email already exists: abc@wp.pl")))
            .andExpect(jsonPath("$.errorTime", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}Z")));
    }

    private String registerCompanyJson() {
        return "{"
            + "\"email\": \"abc@wp.pl\","
            + "\"name\": \"Comp S.A.\","
            + "\"vat\": \"PL920200220\""
            + "}";
    }
}