package com.cece.kcb.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String jwtToken;
    private int expirationTime;
}
