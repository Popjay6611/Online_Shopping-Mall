package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.*;
import com.popoola.shopping.Repos.CartRepo;
import com.popoola.shopping.Repos.OrderRepo;
import com.popoola.shopping.Repos.OrderedProductsRepo;
import com.popoola.shopping.Repos.UserRepo;
import com.popoola.shopping.Servuces.Interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
public class CartService implements ICartService {

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderedProductsRepo orderedProductsRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    UserService userService;



    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void checkout(User user) {
        Orders order = new Orders((user));
        orderRepo.save(order);

        // clear cart and decrease product's stock
        user.getCart().getProducts().forEach(prod -> {
            prod.setCart(null);
            prod.setOrder(order);
            productService.decreaseStock(prod.getProductId(), prod.getCount());
            orderedProductsRepo.save(prod);
        });
    }

    @Override
    @Transactional
    public void addToCart(Collection<OrderedProducts> products, User user) {
        Cart cart = user.getCart();
        products.forEach(prod -> {
            Set<OrderedProducts> set = cart.getProducts();
            Optional<OrderedProducts> old = set.stream().filter(x -> x.getProductId().equals(prod.getProductId())).findFirst();
            OrderedProducts orderedProducts;
            if (old.isPresent()) {
                orderedProducts = old.get();
                orderedProducts.setCount(prod.getCount() + orderedProducts.getCount());
            }else {
                orderedProducts = prod;
                orderedProducts.setCart(cart);
                cart.getProducts().add(orderedProducts);
            }
            orderedProductsRepo.save(orderedProducts);
        });
        cartRepo.save(cart);
    }

    @Override
    @Transactional
    public void delete(Long itemId, User user) {
        if (itemId ==null || user == null) {
            throw new ShoppingException(ResponseEnum.ORDER_STATUS_ERROR);
        }

        var op = user.getCart().getProducts().stream().filter(x -> itemId.equals(x.getProductId())).findFirst();
        op.ifPresent(prod -> {
            prod.setCart(null);
            orderedProductsRepo.deleteById(prod.getId());
        });

    }
}
