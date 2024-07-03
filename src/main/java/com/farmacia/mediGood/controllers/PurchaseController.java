package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.Sale.PurchaseDTO;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.entities.Purchase;
import com.farmacia.mediGood.services.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/history/{emailUser}")
    public ResponseEntity<?> getHistoryUser(@PathVariable @NotNull(message = "El correo electrónico no puede ser nulo.")
                                            @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
                                                    message = "El correo electrónico proporcionado no es válido.")
                                            String emailUser) {
        try {
            List<Purchase> purcheseList = purchaseService.historyPurcheseUser(emailUser);
            return ResponseEntity.ok().body(purcheseList);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getHistoryAll() {
        List<Purchase> purcheseList = purchaseService.historyPurchese();
        return ResponseEntity.ok().body(purcheseList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPurcheseById(@PathVariable @NotNull(message = "El id no puede ser nulo.")
                                            Long id) {
            Purchase purchase = purchaseService.getPurcheseById(id);

            if (purchase==null){
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("No existe esta compra"));
            }
            return  ResponseEntity.ok().body(purchase);

    }



}
