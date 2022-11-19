package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Cart;
import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;

import java.util.Collection;

public interface ICartService {
    Cart getCart(User user);
    void checkout(User user);
    void addToCart(Collection<OrderedProducts> products, User user);
    void delete(Long itemId, User user);
}
