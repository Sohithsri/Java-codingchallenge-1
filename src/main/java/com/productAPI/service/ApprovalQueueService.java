package com.productAPI.service;

import com.productAPI.entities.ApprovalProduct;
import com.productAPI.entities.ApprovalQueue;
import com.productAPI.entities.Product;
import com.productAPI.mapper.GenericMapper;
import com.productAPI.respository.ApprovalQueueRepository;
import com.productAPI.respository.ProductRepository;
import com.productAPI.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ApprovalQueueService {


    @Autowired
    private   ProductRepository productRepository;

    @Autowired
    private  ApprovalQueueRepository approvalQueueRepository;

    @Autowired
    private  GenericMapper mapper;
    private static final Map<OperationType, Function<Product,String>> approveFunctions = new HashMap<>();



    public String approve(Long approvalId){
        approveFunctions.put(OperationType.CREATE,(product)->{
            product.setPostedDate(LocalDate.now());
            productRepository.save(product);
            return null;
        });
        approveFunctions.put(OperationType.UPDATE,(product)->{
            productRepository.save(product);
            return null;
        });
        approveFunctions.put(OperationType.DELETE,(product)->{
            productRepository.delete(product);
            return null;
        });
        ApprovalQueue approvalQueue=approvalQueueRepository.findById(approvalId).orElseThrow(()->
                new RuntimeException("Approval Request not found with Id "+approvalId));
        OperationType requestType=approvalQueue.getOperationType();
        ApprovalProduct approvalProduct=approvalQueue.getProduct();
        Product product=mapper.toProduct(approvalProduct);
        approveFunctions.get(requestType).apply(product);
        approvalQueueRepository.delete(approvalQueue);
        return "Successfully Approved";
    }

    public String reject(Long approvalId){
        ApprovalQueue approvalQueue=approvalQueueRepository.findById(approvalId).orElseThrow(()->
                new RuntimeException("Approval Request not found with Id "+approvalId));
        approvalQueueRepository.delete(approvalQueue);
        return "Successfully Rejected";
    }
}
