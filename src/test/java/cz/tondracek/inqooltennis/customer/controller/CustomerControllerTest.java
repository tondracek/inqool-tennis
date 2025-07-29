package cz.tondracek.inqooltennis.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tondracek.inqooltennis.core.config.SecurityConfig;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cz.tondracek.inqooltennis.customer.CustomerSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_2_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATED_CUSTOMER_DTO;
import static cz.tondracek.inqooltennis.customer.CustomerSample.UPDATE_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService service;

    @Test
    void createCustomer() throws Exception {
        when(service.createCustomer(CREATE_DTO)).thenReturn(CUSTOMER_DTO);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CUSTOMER_DTO)));
    }

    @Test
    void createCustomer_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_shouldReturnConflict() throws Exception {
        when(service.createCustomer(CREATE_DTO)).thenThrow(new ConflictException("Conflict"));

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateCustomer() throws Exception {
        when(service.updateCustomer(CUSTOMER.getId(), UPDATE_DTO)).thenReturn(UPDATED_CUSTOMER_DTO);

        mockMvc.perform(put("/api/customers/{id}", CUSTOMER.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(UPDATED_CUSTOMER_DTO)));
    }

    @Test
    void updateCustomer_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/customers/{id}", CUSTOMER.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomer_shouldReturnConflict() throws Exception {
        when(service.updateCustomer(CUSTOMER.getId(), UPDATE_DTO)).thenThrow(new ConflictException());

        mockMvc.perform(put("/api/customers/{id}", CUSTOMER.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}", CUSTOMER.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_shouldReturnNotFound() throws Exception {
        doThrow(new NotFoundException()).when(service).softDeleteCustomer(any());

        mockMvc.perform(delete("/api/customers/{id}", CUSTOMER.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        when(service.getCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER_DTO);

        mockMvc.perform(get("/api/customers/{id}", CUSTOMER.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CUSTOMER_DTO)));
    }

    @Test
    void getCustomerById_shouldReturnNotFound() throws Exception {
        when(service.getCustomerById(CUSTOMER.getId())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/customers/{id}", CUSTOMER.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDetailDto> result = List.of(CUSTOMER_DTO, CUSTOMER_2_DTO);
        when(service.getAllCustomers()).thenReturn(result);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}
