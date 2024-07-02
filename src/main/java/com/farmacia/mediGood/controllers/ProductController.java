package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.product.ProductDTO;
import com.farmacia.mediGood.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private  final ProductService productService;
    private  final InformationValidator informationValidator;

    public ProductController(ProductService productService, InformationValidator informationValidator) {
        this.productService = productService;
        this.informationValidator = informationValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult) {

        ResponseEntity <?> responseValidation = informationValidator.validateInformation(bindingResult);
        if (responseValidation != null) {
            return responseValidation;
        }

        try {
            productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el producto");
        }

    }

}
