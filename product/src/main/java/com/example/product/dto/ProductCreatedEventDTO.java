package com.example.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEventDTO {

    private Long productId;
    private String category;
    private Integer quantity;

}
