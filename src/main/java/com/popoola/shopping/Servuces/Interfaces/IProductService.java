package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.DTOs.UpdateProductDTO;
import com.popoola.shopping.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Product findOne(Long productId);

    Page<Product> findProductsInStock(Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByCategory(Integer categoryType, Pageable pageable);

    void increaseStock(Long productId, int amount);

    void decreaseStock(Long productId, int amount);

    Product offSale(Long productId);

    Product onSale(Long productId);

    Product update(UpdateProductDTO updateProductDTO);

    Product save(Product product);

    void delete(Long productId);

}
