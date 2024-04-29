package com.productAPI.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "product")
public class Product {
    @Column(name = "productId", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private Long price;
    private Boolean status;
    private LocalDate postedDate;
}
