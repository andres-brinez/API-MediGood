package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.input.Sale.ProductPurchaseDTO;
import com.farmacia.mediGood.models.DTOS.input.Sale.PurchaseDTO;
import com.farmacia.mediGood.models.entities.Product;
import com.farmacia.mediGood.models.entities.Purchase;
import com.farmacia.mediGood.models.entities.PurchaseProduct;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.PurchaseProductRepository;
import com.farmacia.mediGood.repositories.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final ProductService productService;
    private  final UsersService userService;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseProductRepository purchaseProductRepository, ProductService productService, UsersService userService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseProductRepository = purchaseProductRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional
    public Purchase savePurchase(PurchaseDTO purchaseDTO) {

        User user = userService.getUserByEmail(purchaseDTO.getEmailUser());

        if (user == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        Purchase purchase = new Purchase(user);
        purchaseRepository.save(purchase);

        List<PurchaseProduct> listProductPurchase= new ArrayList<>();

        for (ProductPurchaseDTO purchaseProductInformation : purchaseDTO.getProducts()){

            Product product = productService.getProductById(purchaseProductInformation.getId());

            if (product == null) {
                throw new IllegalArgumentException("El producto con id "+purchaseProductInformation.getId()+ " no existe. Por favor verifique la información ingresada" );
            }

            if(product.getQuantity()<purchaseProductInformation.getQuantity()){
                throw new IllegalArgumentException("La cantidad a comprar del producto "+product.getId()+" es mayor a la cantidad del producto disponible que es "+ product.getQuantity());
            }

            PurchaseProduct purchaseProduct = new PurchaseProduct(purchase,product,purchaseProductInformation.getQuantity());
            listProductPurchase.add(purchaseProduct); // Agrega a la lista antes de guardar

            purchaseProductRepository.save(purchaseProduct);
            product.setQuantity(product.getQuantity()-purchaseProductInformation.getQuantity());
            productService.updateStock(product);

        }

        if (listProductPurchase.isEmpty()) {
            throw new IllegalArgumentException("No se agregó ningún producto a la compra");
        }

        // Asigna los PurchaseProduct a la Purchase
        purchase.setProducts(listProductPurchase);

        // Calcula la información de la compra después de que los PurchaseProduct están guardados
        purchase.calculatePurchaseInformation();

        // Finalmente, guarda la Purchase con los PurchaseProduct asociados
        purchaseRepository.save(purchase);

        return purchase;

    }

}
