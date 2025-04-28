package com.cece.kcb.disbursement;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class MpesaProvider implements DisbursementProvider {

    private final Random rand = new Random();


    @Override
    public String getProviderName() {
        return "MPESA";
    }

    //Placeholder for creating a disbursement.
    @Override
    public String createDisbursement(final Double amount, final String recipientName, final String recipientPhone) {
        return UUID.randomUUID().toString();
    }

    //Placeholder for checking the status of a disbursement.
    @Override
    public String checkDisbursementStatus(final String transactionId) {
        int status = rand.nextInt(2);
        return status == 0 ? "FAILED" : "SUCCESS";
    }
}
