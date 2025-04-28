package com.cece.kcb.controller;


import com.cece.kcb.entity.User;
import com.cece.kcb.request.DisbursementRequest;
import com.cece.kcb.response.DisbursementResponse;
import com.cece.kcb.response.KCBResponse;
import com.cece.kcb.service.DisbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/b2c")
@RequiredArgsConstructor
public class DisbursementController {

    private final DisbursementService disbursementService;

    @PostMapping("/disburse")
    public KCBResponse<DisbursementResponse> createDisbursement(@RequestBody DisbursementRequest request) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            DisbursementResponse disbursementResponse = disbursementService.createDisbursement(request, user);
            return new KCBResponse(disbursementResponse);
        }catch (Exception e){
            return new KCBResponse<>(e);
        }
    }


    @GetMapping("/status?{transactionId}")
    public KCBResponse<DisbursementResponse> checkDisbursementStatus(@RequestParam(value = "transactionId") String transactionId) {
        try {
            DisbursementResponse disbursementResponse = disbursementService.checkDisbursementStatus(transactionId);
            return new KCBResponse(disbursementResponse);
        }catch (Exception e){
            return new KCBResponse<>(e);
        }
    }




}
