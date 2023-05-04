package com.shrupp.shrupp.config.security.oauth2;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.config.security.jwt.JwtSetupService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtSetupService jwtSetupService;

    @Value("${client.url}")
    private String clientUrl;

    @Value("${client.endpoint}")
    private String redirectEndPoint;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        StringBuilder sb = new StringBuilder();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        jwtSetupService.addJwtTokensToCookie(response, loginUser);
        String authToken = jwtSetupService.createAuthToken(loginUser);
        sb.append(clientUrl)
                .append("/").append(redirectEndPoint)
                .append("?token=").append(authToken);
        getRedirectStrategy().sendRedirect(request, response, sb.toString());
    }
}
