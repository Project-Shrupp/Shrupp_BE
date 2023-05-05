package com.shrupp.shrupp.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shrupp.shrupp.config.security.jwt.JwtSetupService;
import com.shrupp.shrupp.config.security.jwt.JwtToken;
import com.shrupp.shrupp.domain.member.dto.request.AuthRequest;
import com.shrupp.shrupp.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.shrupp.shrupp.support.docs.ApiDocumentUtils.getDocumentRequest;
import static com.shrupp.shrupp.support.docs.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends RestDocsTest {

    @MockBean
    private JwtSetupService jwtSetupService;

    @Test
    @DisplayName("토큰 생성")
    void generateToken() throws Exception {
        JwtToken exceptedToken = new JwtToken("access", "refresh", "Bearer");
        given(jwtSetupService.createJwtTokenByAuthToken("auth")).willReturn(exceptedToken);

        ResultActions perform =
                mockMvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new AuthRequest("auth"))));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(exceptedToken.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(exceptedToken.getRefreshToken()));

        perform.andDo(print())
                .andDo(document("auth",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("authToken").type(JsonFieldType.STRING).description("인증 토큰")),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰"))));
    }
}