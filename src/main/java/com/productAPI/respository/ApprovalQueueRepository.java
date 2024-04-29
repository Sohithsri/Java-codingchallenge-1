package com.productAPI.respository;

import com.productAPI.entities.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue,Long> {

    @Query("SELECT a from ApprovalQueue a  ORDER BY requestDate DESC")
    List<ApprovalQueue> findApprovalQueues();
}
