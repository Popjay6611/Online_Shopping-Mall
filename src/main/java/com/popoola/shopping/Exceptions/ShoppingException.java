package com.popoola.shopping.Exceptions;

import com.popoola.shopping.Enums.ResponseEnum;

public class ShoppingException  extends RuntimeException{
    private Integer code;

    public ShoppingException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = code;
    }

    public ShoppingException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
