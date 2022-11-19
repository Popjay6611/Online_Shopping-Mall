package com.popoola.shopping.Controllers;

import com.popoola.shopping.Models.DTOs.UpdateProductDTO;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Servuces.Implementation.CategoryService;
import com.popoola.shopping.Servuces.Implementation.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ProductController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/product/get-products")
    public Page<Product> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "3") Integer size){
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findAll(request);
    }

    @PostMapping("product/add-product")
    public ResponseEntity addProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        Product prodExists = productService.findOne(product.getProductId());
        if (prodExists != null){
            bindingResult.rejectValue("id", "error.product",
                    "A product already exists with this Id");
        }
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/product/{id}/edit")
    public ResponseEntity editProduct(@PathVariable("id") Long id,
                                      @Valid @RequestBody UpdateProductDTO product,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!id.equals(product.getId())){
            return ResponseEntity.badRequest().body("No product with this Id");
        }

        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") Long productId){
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
