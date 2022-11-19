package com.popoola.shopping.Controllers;

import com.popoola.shopping.Models.Cart;
import com.popoola.shopping.Models.DTOs.ItemDTO;
import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Models.Wishlists;
import com.popoola.shopping.Servuces.Implementation.ProductService;
import com.popoola.shopping.Servuces.Implementation.UserService;
import com.popoola.shopping.Servuces.Implementation.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("/get-wishlist")
    public Wishlists getWishList(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return wishListService.getWishlist(user);
    }

    @PostMapping("/add-to-wishlist")
    public ResponseEntity<Wishlists> addToWishlist(@RequestBody ItemDTO dto, Principal principal){
        var product = productService.findOne(dto.getProductId());
        User user = userService.findByEmail(principal.getName());

        try{
            wishListService.addToWishlist(product, user);
        } catch (Exception ex){
            ResponseEntity.badRequest().body("Adding to Wishlist failed");
        }

        return ResponseEntity.ok(wishListService.getWishlist(user));
    }

    @PostMapping("/delete-wishlist/{productId}")
    public void deleteItem(@PathVariable("productId") Long productId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        wishListService.delete(productId, user);
    }

}
