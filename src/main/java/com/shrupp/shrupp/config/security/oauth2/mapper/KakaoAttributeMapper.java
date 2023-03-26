package com.shrupp.shrupp.config.security.oauth2.mapper;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoAttributeMapper implements AttributeMapper {
    @Override
    public OAuth2Request mapToDto(Map<String, Object> attributes) {
        String accountId = (attributes.get("id")).toString();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String name = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
        String email = (String) kakaoAccount.get("email");
        return new OAuth2Request(accountId, name, email, AuthProvider.KAKAO);
    }
}
