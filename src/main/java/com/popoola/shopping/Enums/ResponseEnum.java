package com.popoola.shopping.Enums;


public enum ResponseEnum {
    PARAM_ERROR(1, "Parameter Error!"),

    CHAT_DOES_NOT_EXIST(10, "Chat does not exist"),
    PRODUCT_DOES_NOT_EXIST(10, "Product does not exist!"),
    PRODUCT_OUT_OF_STOCK(11, "Not enough products in stock!"),
    PRODUCT_STATUS_ERROR(12, "Status is incorrect!"),
    PRODUCT_OFF_SALE(13,"Product is off sale!"),
    PRODUCT_NOT_IN_CART(14,"Product not in cart!"),
    CART_CHECKOUT_SUCCESS(20, "Checkout successful! "),
    PRODUCT_ALREADY_EXIST(21,"Product already exists in Wishlists"),

    CATEGORY_NOT_FOUND(30, "Category does not exist!"),

    ORDER_NOT_FOUND(60, "Order does not exit!"),
    ORDER_STATUS_ERROR(61, "Status is not correct"),


    VALID_ERROR(50, "Wrong information"),
    USER_NOT_FOUNT(40,"User does not exist!")
    ;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
