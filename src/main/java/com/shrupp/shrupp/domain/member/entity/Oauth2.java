package com.shrupp.shrupp.domain.member.entity;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth2 {
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(nullable = false, unique = true)
    private String account;

    public Oauth2(AuthProvider authProvider, String account) {
        this.authProvider = authProvider;
        this.account = account;
    }
}
