package com.shrupp.shrupp.config.security.oauth2.mapper;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class KakaoAttributeMapper implements AttributeMapper {
    @Override
    public OAuth2Request mapToDto(Map<String, Object> attributes) {
        String accountId = (attributes.get("id")).toString();
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        return new OAuth2Request(accountId, name, email, AuthProvider.KAKAO);
    }
}
