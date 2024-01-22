package com.demo.customerservice.controller;

import com.demo.customerservice.controller.api.BaseController;
import com.demo.customerservice.exception.ResourceNotFoundException;
import com.demo.customerservice.validation.message.Messages;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = { BaseController.class })
public class BaseControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(BaseControllerImpl.class);

    /**
     * Maps Illegal Argument Exception to Bad Request.
     *
     * @param ex the thrown IllegalArgumentException
     */
    @ApiResponse(responseCode = Messages.BAD_REQUEST, description = "bad request",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    public @ResponseBody Map<String, Object>
    handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage(), ex);
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", true);
        result.put("error_message", ex.getMessage());
        return result;
    }

    /**
     * Maps Resource Not Found Exception Not Existing Id.
     *
     * @param ex the thrown ResourceNotFoundException
     */
    @ApiResponse(responseCode = Messages.NOT_FOUND, description = "resource not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ResourceNotFoundException.class })
    public @ResponseBody Map<String, Object>
    handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resource_not_found", true);
        result.put("resource_not_found_message", ex.getMessage());
        return result;
    }
}
