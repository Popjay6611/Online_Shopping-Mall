package com.popoola.shopping.Controllers;


import com.popoola.shopping.Models.Cart;
import com.popoola.shopping.Models.DTOs.ItemDTO;
import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Repos.OrderedProductsRepo;
import com.popoola.shopping.Servuces.Implementation.CartService;
import com.popoola.shopping.Servuces.Implementation.OrderedProductService;
import com.popoola.shopping.Servuces.Implementation.ProductService;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderedProductService orderedProductService;

    @Autowired
    OrderedProductsRepo orderedProductsRepo;

    @PostMapping("/add-to-existing-cart")
    public ResponseEntity<Cart> addToExistingCart(@RequestBody Collection<OrderedProducts> products, Principal principal) {
        User user = userService.findByEmail(principal.getName());

            try{
                cartService.addToCart(products, user);
            } catch (Exception ex){
                ResponseEntity.badRequest().body("Adding to cart failed");
            }

            return ResponseEntity.ok(cartService.getCart(user));
    }

    @GetMapping("/get-cart")
    public Cart getCart(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return cartService.getCart(user);
    }

    @PostMapping("/add-to-cart")
    public boolean addToCart(@RequestBody ItemDTO dto, Principal principal){
        var product = productService.findOne(dto.getProductId());
        try{
            addToExistingCart(Collections.singleton(new OrderedProducts(product, dto.getQuantity())), principal);

        } catch (Exception ex){
            return false;
        }

        return true;
    }

    @PutMapping("/update-cart/{productId}")
    public OrderedProducts updateCartItem(@PathVariable("productId") Long productId, @RequestBody ItemDTO quantity, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        orderedProductService.update(productId, quantity.getQuantity(), user);
        return orderedProductService.findOne(productId, user);
    }

    @PostMapping("/check-out")
    public ResponseEntity checkOut(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        cartService.checkout(user);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/delete-cart/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        cartService.delete(itemId, user);

    }
}
