package com.shrupp.shrupp.config.security.jwt;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.config.security.oauth2.mapper.LoginUserMapper;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.security.Key;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final Key key;
    private final MemberService memberService;
    private final LoginUserMapper loginUserMapper;

    public Authentication getAuthentication(String accessToken) {
        Claims claims = getTokenClaims(accessToken);
        Member member = memberService.findById(Long.parseLong(claims.get("id", String.class)));
        LoginUser loginUser = loginUserMapper.toLoginUser(member);

        return new UsernamePasswordAuthenticationToken(loginUser, "", loginUser.getAuthorities());
    }

    private Claims getTokenClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
