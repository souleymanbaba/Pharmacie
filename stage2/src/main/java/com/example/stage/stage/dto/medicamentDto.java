package com.example.stage.stage.dto;

import com.example.stage.stage.enums.Forme;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class medicamentDto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteimg;

    private String dosage;
    private Forme forme;
    private String dosage_ar;
    private MultipartFile img;
    private Long quantity;

    private String name_ar;
    private String description_ar;

    private int quantiteStock;
}
