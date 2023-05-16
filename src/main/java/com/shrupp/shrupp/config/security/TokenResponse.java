package com.shrupp.shrupp.config.security;

public record TokenResponse(String accessToken,
                            String refreshToken) {
}
