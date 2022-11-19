package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Cart;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Models.Wishlists;

import java.util.Collection;

public interface IWishListService {
    Wishlists getWishlist(User user);
    void addToWishlist(Product product, User user);
    void delete(Long productId , User user);
}
