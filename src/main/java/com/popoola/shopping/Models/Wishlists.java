package com.popoola.shopping.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Wishlists {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wishListId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private User user;


    @ElementCollection(targetClass=Product.class)
    private Set<Product> products = new HashSet<>();

    public Wishlists() {
    }

    public Wishlists( User user) {

        this.user = user;
    }

    public long getCatId() {
        return wishListId;
    }

    public void setCatId(long wishListId) {
        this.wishListId = wishListId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
