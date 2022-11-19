package com.popoola.shopping.Enums;


public enum ProductStatusEnum implements ShoppingEnum {

    UP(0, "Available"),
    DOWN(1, "Out of stock");

    private Integer code;
    private String msg;
    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getStatus(Integer code){
        for(ProductStatusEnum statusEnum : ProductStatusEnum.values()) {
            if(statusEnum.getCode() == code) return statusEnum.getMsg();
        }
        return "";
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
