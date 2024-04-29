package com.productAPI.mapper;

import com.productAPI.dto.ProductRequestDTO;
import com.productAPI.entities.ApprovalProduct;
import com.productAPI.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GenericMapper {
    public Product toEntity(ProductRequestDTO dto){
        Product product=new Product();
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getProductPrice());
        product.setStatus(dto.getStatus());
        return product;
    }

    public Product toProduct(ApprovalProduct approvalProduct){
        Product product=new Product();
        product.setProductId(approvalProduct.getProductId());
        product.setProductName(approvalProduct.getProductName());
        product.setPrice(approvalProduct.getPrice());
        product.setStatus(approvalProduct.getStatus());
        return product;
    }

    public ApprovalProduct toApprovalProduct(Product product){
        ApprovalProduct approvalProduct=new ApprovalProduct();
        approvalProduct.setProductId(product.getProductId());
        approvalProduct.setProductName(product.getProductName());
        approvalProduct.setPrice(product.getPrice());
        approvalProduct.setStatus(product.getStatus());
        return approvalProduct;
    }
}
