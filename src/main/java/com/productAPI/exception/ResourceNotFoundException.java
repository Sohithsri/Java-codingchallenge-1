package com.productAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -1344415629594687540L;


    public ResourceNotFoundException(String message) {
        super(message); // Post not found with
    }

}
