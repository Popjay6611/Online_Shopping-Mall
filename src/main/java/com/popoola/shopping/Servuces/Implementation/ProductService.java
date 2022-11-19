package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ProductStatusEnum;
import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.DTOs.UpdateProductDTO;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Repos.ProductRepo;
import com.popoola.shopping.Servuces.Interfaces.ICategoryService;
import com.popoola.shopping.Servuces.Interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class ProductService implements IProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ICategoryService categoryService;

    @Override
    public Product findOne(Long productId) {
        Product product = productRepo.findByProductId(productId);

        return product;
    }

    @Override
    public Page<Product> findProductsInStock(Pageable pageable) {
        return productRepo.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAllByOrderByProductId(pageable);
    }

    @Override
    public Page<Product> findAllByCategory(Integer categoryId, Pageable pageable) {

        return productRepo.findAllByProductStatusOrderByProductIdAsc(categoryId, pageable);
    }

    @Override
    @Transactional
    public void increaseStock(Long productId, int amount) {
        Product product = findOne(productId);
        if (product == null) throw new ShoppingException((ResponseEnum.PRODUCT_DOES_NOT_EXIST));

        int update = product.getProductStock() + amount;
        product.setProductStock(update);
        productRepo.save(product);

    }

    @Override
    @Transactional
    public void decreaseStock(Long productId, int amount) {
        Product product = findOne(productId);
        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);

        int update = product.getProductStock() - amount;
        if (update <= 0) throw new ShoppingException(ResponseEnum.PRODUCT_OUT_OF_STOCK);

        product.setProductStock(update);
        productRepo.save(product);
    }

    @Override
    @Transactional
    public Product offSale(Long productId) {
        Product product = findOne(productId);
        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);

        if (product.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new ShoppingException(ResponseEnum.PRODUCT_STATUS_ERROR);
        }

        product.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productRepo.save(product);
    }

    @Override
    public Product onSale(Long productId) {
        Product product = findOne(productId);
        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);

        if (product.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new ShoppingException(ResponseEnum.PRODUCT_STATUS_ERROR);

        }

        product.setProductStatus(ProductStatusEnum.UP.getCode());
        return productRepo.save(product);
    }

    @Override
    public Product update(UpdateProductDTO updateProductDTO) {
        Product product = findOne(updateProductDTO.getId());
        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);

        categoryService.findCategory(product.getCategoryId());

        if (product.getProductStatus() > 1) {
            throw new ShoppingException(ResponseEnum.PRODUCT_STATUS_ERROR);
        }

        if (updateProductDTO.getProductDescription() != null && !updateProductDTO.getProductDescription().isEmpty()){
            product.setProductDescription(updateProductDTO.getProductDescription());
        }
        if (updateProductDTO.getProductImage() != null && !updateProductDTO.getProductImage().isEmpty()){
            product.setProductImage(updateProductDTO.getProductImage());
        }
        if (updateProductDTO.getProductName() != null && !updateProductDTO.getProductName().isEmpty()){
            product.setProductName(updateProductDTO.getProductName());
        }
        if (updateProductDTO.getPrice() != null){
            product.setPrice(updateProductDTO.getPrice());
        }
        if (updateProductDTO.getCategoryId() != null){
            product.setCategoryId(updateProductDTO.getCategoryId());
        }
        if (updateProductDTO.getProductStock() != null){
            product.setProductStock(updateProductDTO.getProductStock());
        }
        return productRepo.save(product);
    }

    @Override
    public Product save(Product product) {
        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);

        categoryService.findCategory(product.getCategoryId());

        if (product.getProductStatus() > 1) {
            throw new ShoppingException(ResponseEnum.PRODUCT_STATUS_ERROR);
        }
        return productRepo.save(product);
    }

    @Override
    public void delete(Long productId) {
        Product product = findOne(productId);

        if (product == null) throw new ShoppingException(ResponseEnum.PRODUCT_DOES_NOT_EXIST);
        productRepo.delete(product);
    }
}
