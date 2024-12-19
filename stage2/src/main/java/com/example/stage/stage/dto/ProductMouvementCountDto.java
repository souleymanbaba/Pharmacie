package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Medicaments;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductMouvementCountDto {
    private Medicaments medicaments;
    private int mouvementCount;
}
