package com.productAPI.entities;

import com.productAPI.utils.OperationType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "approval_queue")
public class ApprovalQueue {
    @Column(name = "approvalId", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalId;


    private OperationType operationType;

    private LocalDate requestDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "productId",referencedColumnName = "approvalProductId")
    private ApprovalProduct product;
}
