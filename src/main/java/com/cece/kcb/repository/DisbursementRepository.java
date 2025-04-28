package com.cece.kcb.repository;

import com.cece.kcb.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    Optional<Disbursement> findByTransactionId(String transactionId);
}
