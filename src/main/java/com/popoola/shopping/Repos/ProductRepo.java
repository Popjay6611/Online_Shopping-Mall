package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, String> {
    Product findByProductId(Long id);

   // Products on sale
    Page<Product> findAllByProductStatusOrderByProductIdAsc(Integer productStatus, Pageable pageable);

    // Products by category
    Page<Product> findAllByCategoryIdOrderByProductIdAsc(Integer categoryId, Pageable pageable);

    Page<Product> findAllByOrderByProductId(Pageable pageable);
}
