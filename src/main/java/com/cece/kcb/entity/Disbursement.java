package com.cece.kcb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "disbursements")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime disbursementDate;
    private Double amountDisbursed;
    private String status;
    private String recipientName;
    private String recipientPhone;
    private String transactionId;
    private String providerName;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

}
