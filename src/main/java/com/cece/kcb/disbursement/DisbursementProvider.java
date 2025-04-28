package com.cece.kcb.disbursement;

public interface DisbursementProvider {

    /**
     * Returns the name of the disbursement provider.
     *
     * @return the name of the provider
     */
    String getProviderName();

    /**
     * Initiates a disbursement process.
     *
     * @param amount the amount to be disbursed
     * @param recipientName the name of the recipient
     * @param recipientPhone the phone number of the recipient
     * @return a unique transaction ID for tracking purposes
     */
    String createDisbursement(Double amount, String recipientName, String recipientPhone);


    /**
     * Checks the status of a disbursement.
     *
     * @param transactionId the unique transaction ID
     * @return the status of the disbursement
     */
    String checkDisbursementStatus(String transactionId);


}
