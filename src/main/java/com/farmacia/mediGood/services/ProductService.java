package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.input.product.ProductDTO;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.models.entities.Product;
import com.farmacia.mediGood.repositories.CategoryRepository;
import com.farmacia.mediGood.repositories.ProductRepository;
import org.springframework.stereotype.Service;

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

    public Product createProduct(ProductDTO product) {

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


    /*public Product updateProduct(Long productId, Product product) throws ChangeSetPersister.NotFoundException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setInStock(product.isInStock());
        existingProduct.setQuantity(product.getQuantity());
        return productRepository.save(existingProduct);
    }*/

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsInStock() {
        return productRepository.findByInStock(true);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
}