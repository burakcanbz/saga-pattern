package com.example.product.controller;


import com.example.product.dto.CreateProductDTO;
import com.example.product.dto.UpdateProductDTO;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getProducts() {
        return "get all products";
    }

    @GetMapping("/{productId}")
    public String getProductById(@PathVariable String productId) {
        return "get product by id " + productId;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        try{
            Product product = productService.createProduct(createProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product.toString() + " created successfully");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public String updateProduct(@RequestBody UpdateProductDTO updateProductDTO) {
        return "update product";
    }

    @PatchMapping
    public String partialUpdateProduct(@RequestBody UpdateProductDTO updateProductDTO) {
        return "update product";
    }

    @DeleteMapping
    public String deleteProductById(@RequestBody Long id) {
        return "delete product";
    }
}
