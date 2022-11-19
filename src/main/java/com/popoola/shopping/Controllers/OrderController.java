package com.popoola.shopping.Controllers;

import com.popoola.shopping.Models.Orders;
import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.Orders;
import com.popoola.shopping.Servuces.Implementation.OrderService;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @GetMapping("/order/get-orders")
    public Page<Orders> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 Authentication authentication) {
        PageRequest req = PageRequest.of(page - 1, size);
        Page<Orders> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            orderPage = orderService.findByEmail(authentication.getName(), req);
        } else {
            orderPage = orderService.findAll(req);
        }
        return  orderPage;
    }

    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<Orders> cancelOrder(@PathVariable("id") Long orderId, Authentication authentication) {
        Orders order = orderService.findOne(orderId);
        if(!authentication.getName().equals(order.getCustomerEmail()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/finish/{id}")
    public ResponseEntity<Orders> finishOrder(@PathVariable("id") Long orderId, Authentication authentication) {
        Orders order = orderService.findOne(orderId);
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrder(@PathVariable("id") Long orderId, Authentication authentication){
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"));
        Orders order = orderService.findOne(orderId);
        if(isCustomer && !authentication.getName().equals(order.getCustomerEmail())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(order);
    }
}
