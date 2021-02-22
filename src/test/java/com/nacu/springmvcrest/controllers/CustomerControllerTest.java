package com.nacu.springmvcrest.controllers;

import com.nacu.springmvcrest.api.model.CustomerDTO;
import com.nacu.springmvcrest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.nacu.springmvcrest.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private final String BASE_URL = CustomerController.BASE_URL;
    private final String FIRST_NAME = "Florin";
    private final String LAST_NAME = "Nacu";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customers = Arrays.asList(
                CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(),
                CustomerDTO.builder().firstName("Cristiano").lastName("Ronaldo").build()
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)))
                .andExpect(jsonPath("$.customers[0].firstName", equalTo(FIRST_NAME)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().lastName(LAST_NAME).firstName(FIRST_NAME).build();
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get(BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO returnDto = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).customerUrl(BASE_URL + "/1").build();
        when(customerService.createNewCustomer(customer)).thenReturn(returnDto);

        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO returnDto = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).customerUrl(BASE_URL + "/1").build();
        when(customerService.saveCustomerById(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(put(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void patchCustomer() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME).build();
        CustomerDTO returnDTO = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).customerUrl(BASE_URL + "/1").build();
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}