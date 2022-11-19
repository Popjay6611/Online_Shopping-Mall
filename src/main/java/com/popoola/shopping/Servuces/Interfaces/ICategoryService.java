package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findCategory(Integer categoryType);
    List<Category> findByCategoryTypeList(List<Integer> categoryTypeList);
    Category save(Category category);
}
