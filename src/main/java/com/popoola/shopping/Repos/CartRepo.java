package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
