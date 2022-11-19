package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.OrderedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductsRepo extends JpaRepository<OrderedProducts, Long> {
}
