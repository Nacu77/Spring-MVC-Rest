package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerById(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomerById(Long id);
}
