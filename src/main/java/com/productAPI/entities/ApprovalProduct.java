package com.productAPI.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "approval_product")
public class ApprovalProduct {
    @Column(name = "approvalProductId", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalProductId;

    private Long productId;
    private String productName;
    private Long price;
    private Boolean status;
}
