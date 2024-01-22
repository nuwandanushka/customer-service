package com.demo.customerservice.dto;

import lombok.Data;

import java.util.Date;

/**
 * The Base entity response.
 */
@Data
public class BaseEntityResponse {

    private String id;

    private Date createdDate;

    private Date updatedDate;

    private String createdBy;

    private String modifiedBy;

    private boolean deleted;

}
