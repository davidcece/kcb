package com.cece.kcb.request;

import lombok.Data;

@Data
public class DisbursementRequest {
    private Double amount;
    private String recipientName;
    private String recipientPhone;
    private String providerName;
}
