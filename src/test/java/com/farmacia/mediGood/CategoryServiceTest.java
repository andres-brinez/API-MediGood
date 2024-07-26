package com.farmacia.mediGood;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farmacia.mediGood.models.DTOS.input.category.CategoryDto;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.repositories.CategoryRepository;
import com.farmacia.mediGood.services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Category 1"));
        categories.add(new Category("Category 2"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());
    }

    @Test
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto("New Category");
        Category category = new Category(categoryDto.getName());

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(categoryDto);

        assertNotNull(result);
        assertEquals("New Category", result.getName());
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category("Test Category");
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
        assertEquals(categoryId, result.getId());
    }
}