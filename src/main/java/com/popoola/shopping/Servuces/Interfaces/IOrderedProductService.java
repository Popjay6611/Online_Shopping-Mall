package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;

public interface IOrderedProductService {
    void update(Long itemId, Integer quantity, User user);
    OrderedProducts findOne(Long itemId, User user);
}
