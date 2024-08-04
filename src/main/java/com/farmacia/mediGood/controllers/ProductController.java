package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.product.ProductCreateDTO;
import com.farmacia.mediGood.models.DTOS.input.product.ProductUpdateDTO;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.DTOS.shared.SuccessResponseDTO;
import com.farmacia.mediGood.models.entities.Product;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> createProduct(
                                           @RequestParam("id") long id,
                                           @RequestParam("name") String name,
                                           @RequestParam("price") BigDecimal price,
                                           @RequestParam("description") String description,
                                           @RequestParam("imageFile") MultipartFile imageFile, // Asumiendo que quieres manejar un archivo
                                           @RequestParam("categoryId") Long categoryId,
                                           @RequestParam("inStock") Boolean inStock,
                                           @RequestParam("quantity") Integer quantity
                                            ) {

        // TODO: Hacer las validaciones cuando se crea productCreateDTO, investigar como se hace
        //ResponseEntity <?> responseValidation = informationValidator.validateInformation(bindingResult);
        //if (responseValidation != null) {
          //  return responseValidation;
        //}

        ProductCreateDTO productCreateDTO = new ProductCreateDTO(id, name, price, description, imageFile, categoryId, inStock, quantity);

        Product productCreated= productService.createProduct(productCreateDTO);

        if (productCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el producto");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);


    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {

        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Producto no encontrado"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(product);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductsByName(@PathVariable String name) {

        List<Product> products = productService.getProductsByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("No se encontraron productos con el nombre proporcionado"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("No se encontraron productos"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @GetMapping("/available")
    public ResponseEntity<?> getAvailableProducts() {
        List<Product> products = productService.getProductsInStock();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("No se encontraron productos disponibles"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @GetMapping("/top")
    public ResponseEntity<?> getTopProducts() {
        List<Product> products = productService.getTopProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("No se encontraron productos disponibles"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductUpdateDTO user, BindingResult bindingResult) {

        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

        if (responseValidation != null) {
            return responseValidation;
        }

        try {
            Product updateProduct = productService.updateProduct(user);

            if (updateProduct == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Producto no encontrado"));          }

            return ResponseEntity.ok(updateProduct);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Error al actualizar el usuario"));
        }
    }

    @PatchMapping("/hide/{id}")
    public ResponseEntity<?> hideProduct(@PathVariable Long id) {
        Product product = productService.hideProduct(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Producto no encontrado"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }




}
