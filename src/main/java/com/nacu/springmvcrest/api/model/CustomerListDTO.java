package com.nacu.springmvcrest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListDTO {
    List<CustomerDTO> customers;
}
