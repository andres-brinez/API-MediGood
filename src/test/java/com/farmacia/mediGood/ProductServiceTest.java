package com.farmacia.mediGood;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.farmacia.mediGood.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farmacia.mediGood.models.DTOS.input.product.ProductCreateDTO;
import com.farmacia.mediGood.models.DTOS.input.product.ProductUpdateDTO;
import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.models.entities.Product;
import com.farmacia.mediGood.repositories.CategoryRepository;
import com.farmacia.mediGood.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setCategoryId(1L);
        productCreateDTO.setName("Test Product");
        productCreateDTO.setPrice(BigDecimal.valueOf(10.0));
        productCreateDTO.setDescription("Test Description");
        productCreateDTO.setImageFile(null);
        productCreateDTO.setInStock(true);
        productCreateDTO.setQuantity(10);

        Category category = new Category("Test Category");
        category.setId(1L);

//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
  //      when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());

        //Product result = productService.createProduct(productCreateDTO);
        //assertNotNull(result);
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);
        System.out.println(result);
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    public void testGetProductsByName() {
        String productName = "Test Product";
        List<Product> products = new ArrayList<>();
        products.add(new Product(Long.valueOf(120),productName,BigDecimal.valueOf(100000.0),"Description","",new Category("123"),true,123));

        when(productRepository.findByNameContainingIgnoreCase(productName)).thenReturn(products);

        List<Product> result = productService.getProductsByName(productName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productName, result.get(0).getName());
    }

    // Add more test cases for other methods in the ProductService class as needed
}