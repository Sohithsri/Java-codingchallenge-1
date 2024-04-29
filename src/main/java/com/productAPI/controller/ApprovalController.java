package com.productAPI.controller;

import com.productAPI.entities.ApprovalQueue;
import com.productAPI.respository.ApprovalQueueRepository;
import com.productAPI.service.ApprovalQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "api/products/approval-queue")
public class ApprovalController {

    @Autowired
    private ApprovalQueueService approvalQueueService;

    @Autowired
    private ApprovalQueueRepository approvalQueueRepository;
    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<String> approve(@PathVariable("approvalId") Long approvalId){
        return ResponseEntity.ok(approvalQueueService.approve(approvalId));
    }

    @PutMapping("/{approvalId}/reject")
    public ResponseEntity<String> reject(@PathVariable("approvalId") Long approvalId){
        return ResponseEntity.ok(approvalQueueService.reject(approvalId));
    }

    @GetMapping
    public ResponseEntity<List<ApprovalQueue>> getApprovals(){
        return ResponseEntity.ok(approvalQueueRepository.findApprovalQueues());
    }

}
