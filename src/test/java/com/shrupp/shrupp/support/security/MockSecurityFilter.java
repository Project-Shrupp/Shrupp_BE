package com.shrupp.shrupp.support.security;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.domain.Oauth2;
import jakarta.servlet.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.lang.reflect.Field;

public class MockSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();

        LoginUser loginUser = new LoginUser(createMember(), null, null);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(), loginUser.getAuthorities()));

        chain.doFilter(request, response);
    }

    private static Member createMember() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        Class<Member> memberClass = Member.class;
        try {
            Field id = memberClass.getDeclaredField("id");
            id.setAccessible(true);
            id.set(member, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return member;
    }

    @Override
    public void destroy() {
        SecurityContextHolder.clearContext();
    }

    public void getFilters(MockHttpServletRequest mockHttpServletRequest) {

    }
}
