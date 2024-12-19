package com.example.stage.stage.services.customer;

import com.example.stage.stage.dto.ProductDetailDto;
import com.example.stage.stage.dto.medicamentDto;

import java.util.List;

public interface CustomerProductService {
    List<medicamentDto> getAllProducts(String lang);
    List<medicamentDto> getAllProductByName(String name, String lang);
    ProductDetailDto getProductDetailById(Long productId, String lang);
}
