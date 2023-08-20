package com.shrupp.shrupp.config.security.jwt;

import com.shrupp.shrupp.config.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final Long AUTH_TOKEN_VALIDATION_SECOND = 60L * 1000;
    private static final Long ACCESS_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 60 * 1000;
    private static final Long REFRESH_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 90 * 1000;
    private static final String BEARER_TYPE = "bearer";

    private final Key key;
    private final JwtValidator jwtValidator;

    public JwtToken createJwtToken(LoginUser loginUser) {
        Claims claims = getClaims(loginUser);

        String accessToken = getToken(loginUser, claims, ACCESS_TOKEN_VALIDATION_SECOND);
        String refreshToken = getToken(loginUser, claims, REFRESH_TOKEN_VALIDATION_SECOND);

        return new JwtToken(accessToken, refreshToken, BEARER_TYPE);
    }

    public JwtToken createJwtTokenByAuthToken(String authToken) {
        return createJwtToken(jwtValidator.getLoginUser(authToken));
    }

    public String createAuthToken(LoginUser loginUser) {
        return getToken(loginUser, getClaims(loginUser), AUTH_TOKEN_VALIDATION_SECOND);
    }

    public Claims getClaims(LoginUser loginUser) {
        Claims claims = Jwts.claims();
        claims.put("id", loginUser.getName());
        return claims;
    }

    private String getToken(LoginUser loginUser, Claims claims, Long validationSecond) {
        long now = new Date().getTime();

        return Jwts.builder()
                .setSubject(loginUser.getName())
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now + validationSecond))
                .compact();
    }
}
