package com.shrupp.shrupp.domain.member.dto.response;

import com.shrupp.shrupp.config.security.jwt.JwtToken;

public record AuthResponse(String accessToken,
                           String refreshToken) {

    public static AuthResponse of(JwtToken jwtToken) {
        return new AuthResponse(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
