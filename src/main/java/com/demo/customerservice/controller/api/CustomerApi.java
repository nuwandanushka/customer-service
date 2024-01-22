package com.demo.customerservice.controller.api;

import com.demo.customerservice.dto.CreateUpdateCustomerRequest;
import com.demo.customerservice.dto.CustomerDeleteResponse;
import com.demo.customerservice.dto.CustomerResponse;
import com.demo.customerservice.platform.common.RequestMapper;
import com.demo.customerservice.validation.message.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Customer api.
 */
@Tag(name = "Customer", description = "the Customer Api")
public interface CustomerApi extends BaseController {

    /**
     * Create customer response.
     *
     * @param createUpdateCustomerRequest the create update customer request
     * @return the customer response
     */
    @Operation(
            summary = "Create customers",
            description = "create new customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Request data to create customer")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CustomerResponse createCustomer(
            @RequestBody(required = true) CreateUpdateCustomerRequest createUpdateCustomerRequest);

    /**
     * Update customer response.
     *
     * @param id                          the id
     * @param createUpdateCustomerRequest the create update customer request
     * @return the customer response
     */
    @Operation(
            summary = "Update a customer",
            description = "update a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Request data to update a customer for given Id")
    @PutMapping(value = RequestMapper.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    CustomerResponse updateCustomer(@PathVariable(value = "id") String id,
            @RequestBody(required = true) CreateUpdateCustomerRequest createUpdateCustomerRequest);


    /**
     * delete cstomer.
     *
     * @param id the id
     * @return the customer delete response
     */
    @Operation(summary = "deletes a customer", description = "deletes a customer")
    @ApiResponse(responseCode = Messages.SUCCESS, description = "success",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CustomerDeleteResponse.class)))
    @DeleteMapping(value = RequestMapper.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    CustomerDeleteResponse deleteCustomer(@PathVariable(value = "id") String id);

    /**
     * Find customer by id.
     *
     * @param id the id
     * @return the customer response
     */
    @Operation(summary = "find customer by customer Id",
            description = "find customer by customer Id")
    @ApiResponse(responseCode = Messages.SUCCESS, description = "success",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CustomerResponse.class)))
    @GetMapping(value = RequestMapper.BY_CUSTOMER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomerById(@PathVariable(value = "id") String id);
}
