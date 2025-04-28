package com.cece.kcb.service;

import com.cece.kcb.disbursement.DisbursementProvider;
import com.cece.kcb.entity.Disbursement;
import com.cece.kcb.entity.User;
import com.cece.kcb.repository.DisbursementRepository;
import com.cece.kcb.request.DisbursementRequest;
import com.cece.kcb.response.DisbursementResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DisbursementService {

    private final Map<String, DisbursementProvider> disbursementProviders;
    private final DisbursementRepository disbursementRepository;
    private final SMSService smsService;
    private final UserService userService;

    public DisbursementService(List<DisbursementProvider> disbursementProviders,
                               DisbursementRepository disbursementRepository,
                               SMSService smsService, final UserService userService) {
        this.disbursementProviders = disbursementProviders.stream()
                .collect(Collectors.toMap(DisbursementProvider::getProviderName, provider -> provider));
        this.disbursementRepository = disbursementRepository;
        this.smsService = smsService;
        this.userService = userService;
    }

    public DisbursementResponse createDisbursement(DisbursementRequest request, String username) {
        User sender = userService.getUserByUsername(username);
        DisbursementProvider provider = getProvider(request.getProviderName());
        String transactionId = provider.createDisbursement(request.getAmount(), request.getRecipientName(), request.getRecipientPhone());
        Disbursement disbursement = Disbursement.builder()
                        .disbursementDate(LocalDateTime.now())
                        .amountDisbursed(request.getAmount())
                        .status("PENDING")
                        .recipientName(request.getRecipientName())
                        .recipientPhone(request.getRecipientPhone())
                        .transactionId(transactionId)
                        .providerName(request.getProviderName())
                        .sender(sender)
                        .build();
        disbursement = disbursementRepository.save(disbursement);
        smsService.sendDisbursementNotification(disbursement);
        return new DisbursementResponse(disbursement);
    }


    private DisbursementProvider getProvider(String providerName) {
        DisbursementProvider provider = disbursementProviders.get(providerName.toUpperCase());
        if (provider == null) {
            throw new IllegalArgumentException("No provider found for name: " + providerName);
        }
        return provider;
    }

    public DisbursementResponse checkDisbursementStatus(final String transactionId) {
        Disbursement disbursement = disbursementRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Disbursement not found for transaction ID: " + transactionId));


        if(disbursement.getStatus().equals("SUCCESS") || disbursement.getStatus().equals("FAILED")) {
            return new DisbursementResponse(disbursement);
        }

        DisbursementProvider provider = getProvider(disbursement.getProviderName());
        String status = provider.checkDisbursementStatus(transactionId);
        disbursement.setStatus(status);
        disbursementRepository.save(disbursement);
        return new DisbursementResponse(disbursement);
    }
}
