package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Wishlists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepo extends JpaRepository<Wishlists, Integer> {
}
