package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.Sale.PurchaseDTO;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.entities.Purchase;
import com.farmacia.mediGood.services.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    private  final PurchaseService purchaseService;
    private final InformationValidator informationValidator;

    public PurchaseController(PurchaseService purchaseService, InformationValidator informationValidator) {
        this.purchaseService = purchaseService;
        this.informationValidator = informationValidator;
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePurchase(@Valid @RequestBody PurchaseDTO purchaseDTO, BindingResult bindingResult) {

        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);
        if (responseValidation != null) {
            return responseValidation;
        }

        try {
            Purchase purchase= purchaseService.savePurchase(purchaseDTO);
            return ResponseEntity.ok(purchase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }


}
