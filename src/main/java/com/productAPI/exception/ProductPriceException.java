package com.productAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class ProductPriceException extends RuntimeException{

    private static final long serialVersionUID = -1344415629794687540L;

    public ProductPriceException(String message) {
        super(message);
    }

}
