package com.example.stage.stage.services.admin.adminProduct;

import com.example.stage.stage.dto.MonthlySortiesData;
import com.example.stage.stage.dto.MouvementStockDto;
import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.dto.ProductMouvementCountDto;
import com.example.stage.stage.entity.MouvementStock;
import com.example.stage.stage.entity.Medicaments;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    medicamentDto addProduct (medicamentDto medicamentDto) throws IOException;

    medicamentDto updateProductTranslation(Long productId, medicamentDto medicamentDto);
    List<medicamentDto> getAllProductByName(String name, String lang);
    boolean deleteProduct(Long id);
    medicamentDto getProductById(Long productId, String lang);
    medicamentDto updateProduct(Long productId, medicamentDto medicamentDto) throws IOException;
//    List<medicamentDto> getProductsByCategoryAndSubcategories(Long categoryId, String lang);
    List<medicamentDto> convertToDtoList(List<Medicaments> medicaments, String lang);
    List<medicamentDto> getAllProducts(String lang);
    void addMouvementStock(MouvementStockDto mouvementStockDto);
    List<MouvementStock> getMouvementsByProductId(Long productId);
    void saveProductsFromExcel(MultipartFile file);

}
