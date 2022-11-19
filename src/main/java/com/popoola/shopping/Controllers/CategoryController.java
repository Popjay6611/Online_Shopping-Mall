package com.popoola.shopping.Controllers;

import com.popoola.shopping.Models.Category;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.Response.CategoryPage;
import com.popoola.shopping.Servuces.Implementation.CategoryService;
import com.popoola.shopping.Servuces.Implementation.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("category/add-category").toUriString());
        return ResponseEntity.created(uri).body(categoryService.save(category));

    }

    @GetMapping("/get-categories")
    public ResponseEntity<Object> getCategories(){
       return ResponseEntity.ok(categoryService.findAll());
    }


    @GetMapping("/get-category/{type}")
    public CategoryPage showOne(@PathVariable("type") Integer categoryType,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size) {

        Category cat = categoryService.findCategory(categoryType);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> productByCategory = productService.findAllByCategory(categoryType, request);
        var prods = new CategoryPage("", productByCategory);
        prods.setCategory(cat.getCategoryName());
        return prods;
    }
}
