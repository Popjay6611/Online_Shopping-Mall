package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Models.OrderedProducts;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Repos.OrderedProductsRepo;
import com.popoola.shopping.Servuces.Interfaces.IOrderedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderedProductService implements IOrderedProductService {

    @Autowired
    OrderedProductsRepo orderedProductsRepo;

    @Override
    @Transactional
    public void update(Long itemId, Integer quantity, User user) {
        var product = user.getCart().getProducts().stream().filter(x -> itemId.equals(x.getProductId())).findFirst();
        product.ifPresent(prod -> {
            prod.setCount(quantity);
            orderedProductsRepo.save(prod);
        });
    }

    @Override
    @Transactional
    public OrderedProducts findOne(Long itemId, User user) {
        var product = user.getCart().getProducts().stream().filter(x -> itemId.equals(x.getProductId())).findFirst();
        AtomicReference<OrderedProducts> ref = new AtomicReference<>();
        product.ifPresent(ref::set);
        return ref.get();
    }
}
