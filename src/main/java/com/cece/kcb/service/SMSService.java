package com.cece.kcb.service;

import com.cece.kcb.entity.Disbursement;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    public void sendDisbursementNotification(Disbursement disbursement) {
        String message = "Your disbursement with transaction ID " + disbursement.getTransactionId() + " has been processed.";
        System.out.println("Sending SMS to " + disbursement.getRecipientName() + ": " + message);
    }
}
