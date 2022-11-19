package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    List<Category> findByCategoryIdInOrderByCategoryIdAsc(List<Integer> categoryIds);

    List<Category> findAllByOrderByCategoryId();

    Category findByCategoryId(Integer categoryId);
}
