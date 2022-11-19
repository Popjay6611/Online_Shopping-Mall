package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.Category;
import com.popoola.shopping.Repos.CategoryRepo;
import com.popoola.shopping.Servuces.Interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAllByOrderByCategoryId();
    }

    @Override
    public Category findCategory(Integer categoryId) {
        Category cat = categoryRepo.findByCategoryId(categoryId);
        if (cat == null) throw new ShoppingException(ResponseEnum.CATEGORY_NOT_FOUND);
        return cat;
    }

    @Override
    public List<Category> findByCategoryTypeList(List<Integer> categoryIdList) {
        return categoryRepo.findByCategoryIdInOrderByCategoryIdAsc(categoryIdList);
    }

    @Override
    @Transactional
    public Category save(Category category) {
        return categoryRepo.save(category);
    }
}
