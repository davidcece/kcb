package com.cece.kcb.response;

import com.cece.kcb.entity.Disbursement;
import com.cece.kcb.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisbursementResponse {
    private Long id;
    private LocalDateTime disbursementDate;
    private Double amountDisbursed;
    private String status;
    private String recipientName;
    private String recipientPhone;
    private String transactionId;
    private String providerName;
    private String senderName;

    public DisbursementResponse() {
    }

    public DisbursementResponse(Disbursement disbursement) {
        this.id = disbursement.getId();
        this.disbursementDate = disbursement.getDisbursementDate();
        this.amountDisbursed = disbursement.getAmountDisbursed();
        this.status = disbursement.getStatus();
        this.recipientName = disbursement.getRecipientName();
        this.recipientPhone = disbursement.getRecipientPhone();
        this.transactionId = disbursement.getTransactionId();
        this.providerName = disbursement.getProviderName();
        User sender = disbursement.getSender();
        if (sender != null) {
            this.senderName = sender.getUsername();
        }

    }
}
