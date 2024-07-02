package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.input.product.ProductCreateDTO;
import com.farmacia.mediGood.models.DTOS.input.product.ProductUpdateDTO;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.models.entities.Product;
import com.farmacia.mediGood.repositories.CategoryRepository;
import com.farmacia.mediGood.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository ;
    private final ImageService imageService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    public Product createProduct(ProductCreateDTO product) {

        Optional<Category> category = categoryRepository.findById(product.getCategoryId());

        try {
            var imgUrl = imageService.uploadImage(product.getImageFile());
            Product productToCreate = new Product(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    imgUrl,
                    category.get(),
                    product.isInStock(),
                    product.getQuantity()

            );

            return productRepository.save(productToCreate);
        }
        catch (Exception e) {
            return null;
        }

    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // get all products in stock
    public List<Product> getProductsInStock() {
        return productRepository.findByInStock(true);
    }

    public List<Product> getTopProducts() {
        return productRepository.findTop15ByOrderByDateAddedAsc();
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    // Este metodo es parecido al de update para el usuario que tiene comentarios
    public Product updateProduct(ProductUpdateDTO payload) {

        Optional<Product> optionalProduct = productRepository.findById(payload.getId());

        if (optionalProduct.isEmpty()) {
            return null;
        }

        Product productDB = optionalProduct.get();

        for (Field productDbField : productDB.getClass().getDeclaredFields()) {
            productDbField.setAccessible(true);

            for (Field ProductField : payload.getClass().getDeclaredFields()) {
                ProductField.setAccessible(true);

                if(productDbField.getName().equals(ProductField.getName())) {


                    validateObject(productDB, payload, productDbField, ProductField);
                }
            }
        }

        return productRepository.save(productDB);
    }

   // hidden product

    public Product hideProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return null;
        }
        Product productDB = optionalProduct.get();
        productDB.setInStock(false);
        return productRepository.save(productDB);
    }



    private void validateObject(Product productDB, ProductUpdateDTO producto, Field fieldProductDb, Field fieldProduct) {
        try {
            // Si el campo no está vacío
            if (fieldProduct.get(producto) != null) {
                fieldProductDb.set(productDB, fieldProduct.get(producto));
            }
        } catch (IllegalAccessException e) {
            // Maneja la excepción
            e.printStackTrace();
        }
    }
}

