package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.input.category.CategoryDto;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDto payload ) {

        Category category = new Category(payload.getName());
        return categoryRepository.save(category);
    }


    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
