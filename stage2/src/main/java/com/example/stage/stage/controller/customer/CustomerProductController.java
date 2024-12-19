package com.example.stage.stage.controller.customer;

import com.example.stage.stage.dto.ProductDetailDto;
import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.services.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<medicamentDto>> getAllProducts(){
        List<medicamentDto> medicamentDtos = customerProductService.getAllProducts("fr");
        return ResponseEntity.ok(medicamentDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<medicamentDto>> getAllProductByName(@PathVariable String name) {
        List<medicamentDto> medicamentDtos = customerProductService.getAllProductByName(name,"fr");
        return ResponseEntity.ok (medicamentDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId) {
        ProductDetailDto productDetailDto= customerProductService.getProductDetailById(productId,"fr");
        if(productDetailDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDetailDto);
    }
}
