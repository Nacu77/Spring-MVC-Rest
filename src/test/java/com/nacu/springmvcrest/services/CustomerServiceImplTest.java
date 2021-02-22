package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.mapper.CustomerMapper;
import com.nacu.springmvcrest.api.model.CustomerDTO;
import com.nacu.springmvcrest.domain.Customer;
import com.nacu.springmvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    private final Long ID = 1L;
    private final String FIRST_NAME = "Florin";
    private final String LAST_NAME = "Nacu";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).build(),
                Customer.builder().id(2L).firstName("John").lastName("Cena").build()
        );
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(2, customerDTOS.size());
        assertEquals(FIRST_NAME, customerDTOS.get(0).getFirstName());
        assertEquals(LAST_NAME, customerDTOS.get(0).getLastName());
        assertEquals("/api/customers/" + ID, customerDTOS.get(0).getCustomerUrl());
    }

    @Test
    void getCustomerById() {
        Customer customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).build();
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

}