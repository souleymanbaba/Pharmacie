package com.example.stage.stage.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedProductsResponseDto {
    private List<medicamentDto> medicamentDtoList;
    private  Long orderAmount;
}
