package com.productAPI.respository;

import com.productAPI.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
    @Query("SELECT p from Product p WHERE status=true ORDER BY postedDate DESC")
    List<Product> findActiveProducts();

}
