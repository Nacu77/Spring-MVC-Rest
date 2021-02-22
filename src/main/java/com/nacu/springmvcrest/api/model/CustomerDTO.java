package com.nacu.springmvcrest.api.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private String firstName;
    private String lastName;
    private String customerUrl;

    @Builder
    public CustomerDTO(String firstName, String lastName, String customerUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerUrl = customerUrl;
    }
}
