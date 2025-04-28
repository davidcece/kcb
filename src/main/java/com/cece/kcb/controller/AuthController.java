package com.cece.kcb.controller;

import com.cece.kcb.request.LoginRegisterRequest;
import com.cece.kcb.response.KCBResponse;
import com.cece.kcb.response.TokenResponse;
import com.cece.kcb.service.UserService;
import com.cece.kcb.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public KCBResponse<Void> register(@RequestBody LoginRegisterRequest request) {
        try{
            if (userService.existsByUsername(request.getUsername())) {
                return new KCBResponse<>("Username already exists");
            }
            userService.register(request.getUsername(), request.getPassword());
            return new KCBResponse<>("Register success");
        } catch (Exception e) {
            return new KCBResponse<>(e);
        }
    }


    @PostMapping("/authenticate")
    public KCBResponse<TokenResponse> login(@RequestBody LoginRegisterRequest request) {
        try {
            if (!userService.validateCredentials(request.getUsername(), request.getPassword())) {
                return new KCBResponse<>(new RuntimeException("Invalid username or password"));
            }
            TokenResponse tokenResponse = TokenResponse.builder()
                    .jwtToken(jwtUtil.generateToken(request.getUsername()))
                    .expirationTime(3600)
                    .build();
            return new KCBResponse<>(tokenResponse);
        }catch (Exception e) {
            return new KCBResponse<>(e);
        }
    }
}