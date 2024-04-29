package com.productAPI.controller;

import com.productAPI.dto.ProductRequestDTO;
import com.productAPI.entities.Product;
import com.productAPI.respository.ProductRepository;
import com.productAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.createProduct(productRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getActive(){
        return ResponseEntity.ok(productRepository.findActiveProducts());
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) LocalDate minPostedDate,
            @RequestParam(required = false) LocalDate maxPostedDate
    ) {
        Specification<Product> specification = productService.buildSearchSpecification(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
        return productRepository.findAll(specification);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDTO productRequestDTO,@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.updateProduct(productRequestDTO,productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}
