package com.productAPI.service;

import com.productAPI.dto.ProductRequestDTO;
import com.productAPI.entities.ApprovalProduct;
import com.productAPI.entities.ApprovalQueue;
import com.productAPI.entities.Product;
import com.productAPI.exception.ProductPriceException;
import com.productAPI.exception.ResourceNotFoundException;
import com.productAPI.mapper.GenericMapper;
import com.productAPI.respository.ApprovalQueueRepository;
import com.productAPI.respository.ProductRepository;
import com.productAPI.utils.OperationType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ApprovalQueueRepository approvalQueueRepository;

    private final GenericMapper mapper;

    public ProductService(ProductRepository productRepository, ApprovalQueueRepository approvalQueueRepository, GenericMapper mapper) {
        this.productRepository = productRepository;
        this.approvalQueueRepository = approvalQueueRepository;
        this.mapper = mapper;
    }

    public String createProduct(ProductRequestDTO productRequestDTO){
        if(productRequestDTO.getProductPrice()>10000){
            throw new ProductPriceException("Product price should not exceed $10000");
        }
        Product product=mapper.toEntity(productRequestDTO);
        product.setPostedDate(LocalDate.now());
        if(productRequestDTO.getProductPrice()>5000){
            addToApprovalQueue(product,OperationType.CREATE);
            return "Request Added for Approval Queue";
        }
        productRepository.save(product);
        return "Product Created Successfully";
    }

    public String updateProduct(ProductRequestDTO productRequestDTO,Long productId){
        Product product=productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product not found with Id "+productId)
        );
        double thresholdPrice=product.getPrice()+(product.getPrice()*0.5);
        product.setProductName(productRequestDTO.getProductName());
        product.setPrice(productRequestDTO.getProductPrice());
        product.setStatus(productRequestDTO.getStatus());
        if(productRequestDTO.getProductPrice()>thresholdPrice){
            addToApprovalQueue(product,OperationType.UPDATE);
            return "Request Added for Approval Queue";
        }
        productRepository.save(product);
        return "Product Updated Successfully";
    }

    public String deleteProduct(Long productId){
        Product product=productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product not found with Id "+productId)
        );
        addToApprovalQueue(product,OperationType.DELETE);
        return "Request Added for Approval Queue";
    }

    private void addToApprovalQueue(Product product, OperationType operationType){
        ApprovalQueue approvalQueue=new ApprovalQueue();
        ApprovalProduct approvalProduct=mapper.toApprovalProduct(product);
        approvalQueue.setProduct(approvalProduct);
        approvalQueue.setOperationType(operationType);
        approvalQueue.setRequestDate(LocalDate.now());
        approvalQueueRepository.save(approvalQueue);
    }
    public Specification<Product> buildSearchSpecification(String productName, Long minPrice, Long maxPrice, LocalDate minPostedDate, LocalDate maxPostedDate) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productName != null && !productName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + productName.toLowerCase() + "%"));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minPostedDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("postedDate"), minPostedDate));
            }

            if (maxPostedDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("postedDate"), maxPostedDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
