package com.shrupp.shrupp.domain.member.controller;

import com.shrupp.shrupp.config.security.jwt.JwtSetupService;
import com.shrupp.shrupp.domain.member.dto.request.AuthRequest;
import com.shrupp.shrupp.domain.member.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtSetupService jwtSetupService;

    @PostMapping
    public ResponseEntity<AuthResponse> authToken(@RequestBody @Validated AuthRequest authRequest) {
        return ResponseEntity.ok(AuthResponse.of(jwtSetupService.createJwtTokenByAuthToken(authRequest.authToken())));
    }
}
