package com.shrupp.shrupp.config.security.oauth2.mapper;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.domain.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LoginUserMapper {
    public LoginUser toLoginUser(Member member) {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("id", member.getId());
        return new LoginUser(member, attributes, member.getRole());
    }
}
