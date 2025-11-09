package com.example.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String productName;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String productDescription;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price is too high")
    private Double productPrice;  // ← String değil Double

    @NotBlank(message = "Category cannot be empty")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String productCategory;

    @NotBlank(message = "Quantity cannot be empty")
    private Integer productQuantity;
}
