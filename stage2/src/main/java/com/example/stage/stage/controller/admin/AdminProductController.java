package com.example.stage.stage.controller.admin;

import com.example.stage.stage.dto.MonthlySortiesData;
import com.example.stage.stage.dto.MouvementStockDto;
import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.dto.ProductMouvementCountDto;
import com.example.stage.stage.entity.MouvementStock;
import com.example.stage.stage.services.admin.adminProduct.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class  AdminProductController {

    private final ProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<medicamentDto> addProduct(@ModelAttribute medicamentDto medicamentDto) throws IOException {
        medicamentDto medicamentDto1 = adminProductService.addProduct(medicamentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentDto1);
    }


    @GetMapping( "/ouvert/products")
    public ResponseEntity<List<medicamentDto>> getAllProducts(@RequestParam(defaultValue = "fr") String lang) {
        List<medicamentDto> medicamentDto = adminProductService.getAllProducts(lang);
        return ResponseEntity.ok(medicamentDto);
    }

    @PostMapping("/mouvement")
    public void addMouvementStock(@RequestBody MouvementStockDto mouvementStockDto) {
        adminProductService.addMouvementStock(mouvementStockDto);
    }





    @PutMapping("/product/{productId}/translation")
    public ResponseEntity<medicamentDto> updateProductTranslation(@PathVariable Long productId,
                                                                  @RequestBody medicamentDto medicamentDto,
                                                                  @RequestParam(defaultValue = "fr") String lang) {
        medicamentDto updatedProduct = adminProductService.updateProductTranslation(productId, medicamentDto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/ouvert/category/{categoryId}")
//    public ResponseEntity<List<medicamentDto>> getProductsByCategoryAndSubcategories(@PathVariable Long categoryId,
//                                                                                     @RequestParam(defaultValue = "fr") String lang) {
//        List<medicamentDto> products = adminProductService.getProductsByCategoryAndSubcategories(categoryId, lang);
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<medicamentDto>> getAllProductByName(@PathVariable String name,
                                                                   @RequestParam(defaultValue = "fr") String lang) {
        List<medicamentDto> medicamentDtos = adminProductService.getAllProductByName(name, lang);
        return ResponseEntity.ok(medicamentDtos);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = adminProductService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            adminProductService.saveProductsFromExcel(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and products saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and save products: " + e.getMessage());
        }
    }



    @GetMapping("/product/{productId}")
    public ResponseEntity<medicamentDto> getProductById(@PathVariable Long productId,
                                                        @RequestParam(defaultValue = "fr") String lang) {
        medicamentDto medicamentDto = adminProductService.getProductById(productId, lang);
        if (medicamentDto != null) {
            return ResponseEntity.ok(medicamentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<medicamentDto> updateProduct(@PathVariable Long productId,
                                                       @ModelAttribute medicamentDto medicamentDto,
                                                       @RequestParam(defaultValue = "fr") String lang) throws IOException {
        medicamentDto updatedProduct = adminProductService.updateProduct(productId, medicamentDto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mouvements/{productId}")
    public List<MouvementStock> getMouvementsByProductId(@PathVariable Long productId) {
        return adminProductService.getMouvementsByProductId(productId);
    }
}
