package com.shrupp.shrupp.config.security;

import com.shrupp.shrupp.domain.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class LoginUser implements UserDetails, OAuth2User {
    private final Member member;
    private final Map<String, Object> attribute;
    private final Collection<? extends GrantedAuthority> authorities;

    public LoginUser(Member member, Map<String, Object> attribute, Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.attribute = attribute;
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return member.getId().toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
