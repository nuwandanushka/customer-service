package com.demo.customerservice.dto;

import lombok.Data;

/**
 * The Create update customer request.
 */
@Data
public class CreateUpdateCustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNum;

}
