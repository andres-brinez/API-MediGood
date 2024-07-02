package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.category.CategoryDto;
import com.farmacia.mediGood.models.DTOS.input.category.IdCategoryDTO;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.DTOS.shared.SuccessResponseDTO;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final InformationValidator informationValidator;

    public CategoryController(CategoryService categoryService, InformationValidator informationValidator) {
        this.categoryService = categoryService;
        this.informationValidator = informationValidator;
    }

    @GetMapping
    public List<Category> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();

        return categoryService.getAllCategories();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {

        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

        if (responseValidation != null) {
            return responseValidation;
        }

        try {
            Category category = categoryService.createCategory(categoryDto);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Error creating category"));
        }
    }

    // get by id
    @GetMapping("/getById")
    public ResponseEntity<?> getCategoryById( @Valid @RequestBody IdCategoryDTO id, BindingResult bindingResult) {

        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

        if (responseValidation != null) {
            return responseValidation;
        }

        Category category = categoryService.getCategoryById(id.getId());

        if (category == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Category not found"));
        }

        return ResponseEntity.ok(category);
    }

    }
