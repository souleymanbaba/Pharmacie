package com.example.stage.stage.services.customer;

import com.example.stage.stage.dto.ProductDetailDto;
import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.entity.Medicaments;
import com.example.stage.stage.repostory.ProcuctRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {
    private final ProcuctRepository productRepository;


    public List<medicamentDto> getAllProducts(String lang) {
        List<Medicaments> medicaments = productRepository.findAll();
        return medicaments.stream().map(product -> product.getDto(lang)).collect(Collectors.toList());
    }


    public List<medicamentDto> getAllProductByName(String name, String lang) {
        List<Medicaments> medicaments = productRepository.findAllByNameContaining(name);
        return medicaments.stream().map(product -> product.getDto(lang)).collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetailById(Long productId, String lang) {
        Optional<Medicaments> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setMedicamentDto(optionalProduct.get().getDto(lang));

            return productDetailDto;
        }
        return null;
    }


}
