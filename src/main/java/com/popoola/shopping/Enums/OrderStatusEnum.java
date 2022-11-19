package com.popoola.shopping.Enums;

public enum OrderStatusEnum implements ShoppingEnum{
    NEW(0, "New Order"),
    Finished(1, "Finished"),
    CANCELED(2, "Canceled");

    private int code;
    private String msg;
    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
