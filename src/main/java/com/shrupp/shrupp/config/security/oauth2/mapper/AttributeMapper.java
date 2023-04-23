package com.shrupp.shrupp.config.security.oauth2.mapper;

import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;

import java.util.Map;

public interface AttributeMapper {
    OAuth2Request mapToDto(Map<String, Object> attributes);
}
