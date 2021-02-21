package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
}
