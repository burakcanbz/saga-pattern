package com.example.product.service;

import com.example.product.dto.CreateProductDTO;
import com.example.product.dto.ProductCreatedEventDTO;
import com.example.product.entity.Product;
import com.example.product.messaging.ProductPublisher;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPublisher productPublisher;

    public Product createProduct(CreateProductDTO createProductDTO) {

        Product product = productRepository.findByProductName(createProductDTO.getProductName());
        if (product != null) {
            throw new RuntimeException("Product already exists");
        }

        Product newProduct = Product.builder()
                .productName(createProductDTO.getProductName())
                .productCategory(createProductDTO.getProductCategory())
                .productDescription(createProductDTO.getProductDescription())
                .productPrice(createProductDTO.getProductPrice())
                .build();


        Product savedProduct = productRepository.save(newProduct);

        productPublisher.publishProductCreated(
                ProductCreatedEventDTO.builder()
                    .productId(savedProduct.getId())
                    .category(savedProduct.getProductCategory())
                    .quantity(createProductDTO.getProductQuantity())
                    .build()
        );

        return savedProduct;

    }

}
