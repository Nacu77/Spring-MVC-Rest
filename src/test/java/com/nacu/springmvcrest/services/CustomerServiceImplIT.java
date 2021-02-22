package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.mapper.CustomerMapper;
import com.nacu.springmvcrest.api.model.CustomerDTO;
import com.nacu.springmvcrest.bootstrap.Bootstrap;
import com.nacu.springmvcrest.domain.Customer;
import com.nacu.springmvcrest.repositories.CategoryRepository;
import com.nacu.springmvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomerUpdateFirstName() {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = CustomerDTO.builder().firstName(updatedName).build();
        customerService.patchCustomer(id, customerDTO);
        Customer updatedCustomer = customerRepository.findById(id).orElse(null);

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertNotEquals(originalFirstName, updatedCustomer.getFirstName());
        assertEquals(originalLastName, updatedCustomer.getLastName());
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        return customers.get(0).getId();
    }
}
