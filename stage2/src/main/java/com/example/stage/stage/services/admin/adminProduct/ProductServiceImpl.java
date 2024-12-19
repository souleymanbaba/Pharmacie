package com.example.stage.stage.services.admin.adminProduct;

import com.example.stage.stage.dto.MonthlySortiesData;
import com.example.stage.stage.dto.MouvementStockDto;
import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.dto.ProductMouvementCountDto;
import com.example.stage.stage.entity.Category;
import com.example.stage.stage.entity.Marque;
import com.example.stage.stage.entity.Medicaments;
import com.example.stage.stage.entity.MouvementStock;
import com.example.stage.stage.repostory.CategoryRepository;
import com.example.stage.stage.repostory.MarqueRepository;
import com.example.stage.stage.repostory.MouvementStockRepository;
import com.example.stage.stage.repostory.ProcuctRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Logger;
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProcuctRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final MouvementStockRepository mouvementStockRepository;

    private final MarqueRepository marqueRepository;



    public void addMouvementStock(MouvementStockDto mouvementStockDto) {
        Medicaments medicaments = productRepository.findById(mouvementStockDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Medicaments not found"));

        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setMedicaments(medicaments);
        mouvementStock.setType(mouvementStockDto.getType());
        mouvementStock.setQuantite(mouvementStockDto.getQuantite());
        mouvementStock.setDateMouvement(mouvementStockDto.getDateMouvement());
        mouvementStock.setRemarque(mouvementStockDto.getRemarque());
        mouvementStockRepository.save(mouvementStock);
        medicaments.updateStockQuantity(mouvementStockDto.getQuantite(), mouvementStockDto.getType());

        productRepository.save(medicaments);
    }

    public medicamentDto addProduct(medicamentDto medicamentDto) throws IOException {
        Medicaments medicaments = new Medicaments();
        medicaments.setName(medicamentDto.getName());
        medicaments.setDescription(medicamentDto.getDescription());
        medicaments.setPrice(medicamentDto.getPrice());
        medicaments.setImg(medicamentDto.getImg().getBytes());


        medicaments.setName_ar(medicamentDto.getName_ar());
        medicaments.setDescription_ar(medicamentDto.getDescription_ar());


        medicaments.setQuantiteStock(medicamentDto.getQuantiteStock());
        return productRepository.save(medicaments).getDto("fr");
    }

    public List<medicamentDto> getAllProducts(String lang) {
        List<Medicaments> medicaments = productRepository.findAll();
        return medicaments.stream().map(product -> product.getDto(lang)).collect(Collectors.toList());
    }

    public List<medicamentDto> getAllProductByName(String name, String lang) {
        List<Medicaments> medicaments = productRepository.findAllByNameContaining(name);
        return medicaments.stream().map(product -> product.getDto(lang)).collect(Collectors.toList());
    }

//    public List<medicamentDto> getProductsByCategoryAndSubcategories(Long categoryId, String lang) {
//        List<Medicaments> medicaments = productRepository.findByCategoryAndSubcategories(categoryId);
//        return convertToDtoList(medicaments, lang);
//    }


    public List<medicamentDto> convertToDtoList(List<Medicaments> medicaments, String lang) {
        List<medicamentDto> medicamentDtos = new ArrayList<>();
        for (Medicaments medicament : medicaments) {
            medicamentDto medicamentDto = new medicamentDto();
            medicamentDto.setId(medicament.getId());
            if("ar".equals(lang) && medicament.getName_ar()!=null )  medicamentDto.setName(medicament.getName_ar());
            else  medicamentDto.setName( medicament.getName());
            medicamentDto.setPrice(medicament.getPrice());
            if(medicament.getDescription_ar() !=null && "ar".equals(lang) )  medicamentDto.setDescription(medicament.getDescription_ar());
            else medicamentDto.setDescription( medicament.getDescription());
            medicamentDto.setByteimg(medicament.getImg());

            if ("ar".equals(lang) && medicament.getDosage_ar() != null) {
                medicamentDto.setDosage(medicament.getDosage_ar());
            } else {
                medicamentDto.setDosage(medicament.getDosage());
            }
            medicamentDto.setForme(medicament.getForme());
            medicamentDtos.add(medicamentDto);
        }
        return medicamentDtos;
    }

    public void saveProductsFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                Medicaments medicaments = new Medicaments();
                medicaments.setName(row.getCell(0).getStringCellValue());
                medicaments.setPrice((long) row.getCell(1).getNumericCellValue());
                String marque = row.getCell(2).getStringCellValue();
                medicaments.setDescription(row.getCell(4).getStringCellValue());
                medicaments.setQuantiteStock((int) row.getCell(5).getNumericCellValue());

                Marque marque1 = marqueRepository.findByNom(marque);
                if (marque1 == null) {
                    marque1 = marqueRepository.findByNom("autre");
                    if (marque1 == null) {


                    throw new RuntimeException("Erreur : la marque " + marque + " n'a pas été trouvée.");
                    }
                }

                String categoryName = row.getCell(6).getStringCellValue();
                Category category = categoryRepository.findByName(categoryName);
                if (category == null) {
                    category = categoryRepository.findByName("autre");
                    if (category == null) {
                        throw new RuntimeException("Erreur : la catégorie " + categoryName + " et la catégorie 'autre' n'ont pas été trouvées.");
                    }
                }



                productRepository.save(medicaments);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
    }


    public boolean deleteProduct(Long id) {
        Optional<Medicaments> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return optionalProduct.isPresent();
    }

    public medicamentDto getProductById(Long productId, String lang) {
        Optional<Medicaments> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(product -> product.getDto(lang)).orElse(null);
    }

    public medicamentDto updateProduct(Long productId, medicamentDto medicamentDto) throws IOException {
        Optional<Medicaments> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Medicaments medicaments = optionalProduct.get();
            medicaments.setName(medicamentDto.getName());
            medicaments.setPrice(medicamentDto.getPrice());
            medicaments.setDescription(medicamentDto.getDescription());

            medicaments.setName_ar(medicamentDto.getName_ar());
            medicaments.setDescription_ar(medicamentDto.getDescription_ar());
            medicaments.setDosage_ar(medicamentDto.getDosage_ar());
            medicaments.setForme(medicamentDto.getForme());
            if (medicamentDto.getImg() != null) {
                medicaments.setImg(medicamentDto.getImg().getBytes());
            }
            return productRepository.save(medicaments).getDto("fr");
        } else {
            return null;
        }
    }


    private static final Logger logger = Logger.getLogger(ProductService.class.getName());




    public medicamentDto updateProductTranslation(Long productId, medicamentDto medicamentDto) {
        Optional<Medicaments> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Medicaments medicaments = optionalProduct.get();
            if (medicamentDto.getName_ar() != null) {
                medicaments.setName_ar(medicamentDto.getName_ar());
            }
            if (medicamentDto.getDescription_ar() != null) {
                medicaments.setDescription_ar(medicamentDto.getDescription_ar());
            }

            if (medicamentDto.getDosage_ar() != null) {
                medicaments.setDosage_ar(medicamentDto.getDosage_ar());
            }
            return productRepository.save(medicaments).getDto("ar");
        } else {
            return null;
        }
    }


    public List<MouvementStock> getMouvementsByProductId(Long productId) {
        return mouvementStockRepository.findByMedicamentsId(productId);
    }



}
