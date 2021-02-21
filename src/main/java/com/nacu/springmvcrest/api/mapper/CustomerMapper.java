package com.nacu.springmvcrest.api.mapper;

import com.nacu.springmvcrest.api.model.CustomerDTO;
import com.nacu.springmvcrest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
