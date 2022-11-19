package com.popoola.shopping.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShoppingNotFound extends RuntimeException {
    public ShoppingNotFound(String msg) {
        super(msg);
    }
}
