package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Models.Wishlists;
import com.popoola.shopping.Repos.WishListRepo;
import com.popoola.shopping.Servuces.Interfaces.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
public class WishListService implements IWishListService {

    @Autowired
    WishListRepo wishListRepo;

    @Override
    public Wishlists getWishlist(User user) {
        return user.getWishlists();
    }

    @Override
    public void addToWishlist(Product product, User user) {
        Wishlists wishlists = user.getWishlists();
        Collection<Product> set = wishlists.getProducts();
        Optional<Product> old = set.stream().filter(x -> x.getProductId().equals(product.getProductId())).findFirst();
        Product prod;
        if (old.isPresent()){
           throw new ShoppingException(ResponseEnum.PRODUCT_ALREADY_EXIST);

        }else{
            prod = product;
            wishlists.getProducts().add(prod);
        }
        wishListRepo.save(wishlists);
    }



    @Override
    @Transactional
    public void delete(Long productId, User user) {
        if(productId == null || user == null) {
            throw new ShoppingException(ResponseEnum.PRODUCT_STATUS_ERROR);
        }

    var product = user.getWishlists().getProducts().stream().filter(x -> productId.equals(x.getProductId())).findFirst();
    product.ifPresent(prod -> {
        user.getWishlists().getProducts().remove(prod);
//        wishListRepo.deleteById(Math.toIntExact(prod.getProductId()));

    });
    }}
