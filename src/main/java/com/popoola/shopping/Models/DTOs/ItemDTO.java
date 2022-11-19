package com.popoola.shopping.Models.DTOs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ItemDTO {
    @Min(value = 1)
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @NotEmpty
    private Long productId;
}
