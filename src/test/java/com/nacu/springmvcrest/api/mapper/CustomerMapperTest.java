package com.nacu.springmvcrest.api.mapper;

import com.nacu.springmvcrest.api.model.CustomerDTO;
import com.nacu.springmvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Florin";
    public static final String LAST_NAME = "Nacu";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    void customerDtoToCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).customerUrl("/api/customers/" + ID).build();
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
    }
}