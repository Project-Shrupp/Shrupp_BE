package com.shrupp.shrupp.config.security.oauth2.mapper;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class AttributeMapperFactory {

    private final Map<AuthProvider, AttributeMapper> mapperMap =
            new EnumMap<>(AuthProvider.class);
    private final KakaoAttributeMapper kakaoAttributeMapper;

    public AttributeMapperFactory(KakaoAttributeMapper kakaoAttributeMapper) {
        this.kakaoAttributeMapper = kakaoAttributeMapper;
        initialize();
    }

    private void initialize() {
        mapperMap.put(AuthProvider.KAKAO, kakaoAttributeMapper);
    }

    public AttributeMapper getAttributeMapper(AuthProvider authProvider) {
        return mapperMap.get(authProvider);
    }
}
