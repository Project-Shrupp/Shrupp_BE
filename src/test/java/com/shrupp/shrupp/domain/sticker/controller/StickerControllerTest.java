package com.shrupp.shrupp.domain.sticker.controller;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.domain.Oauth2;
import com.shrupp.shrupp.domain.sticker.domain.Sticker;
import com.shrupp.shrupp.domain.sticker.dto.request.StickerAddRequest;
import com.shrupp.shrupp.domain.sticker.service.StickerService;
import com.shrupp.shrupp.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.shrupp.shrupp.support.docs.ApiDocumentUtils.getDocumentRequest;
import static com.shrupp.shrupp.support.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StickerController.class)
class StickerControllerTest extends RestDocsTest {

    @MockBean
    private StickerService stickerService;

    @Test
    @DisplayName("스티커 추가")
    void addSticker() throws Exception {
        Sticker expectedSticker = new Sticker("smile", 0.2, 1.5, new Member("", new Oauth2(AuthProvider.KAKAO, "")));
        given(stickerService.save(any(StickerAddRequest.class), any(Long.class))).willReturn(expectedSticker);

        ResultActions perform =
                mockMvc.perform(post("/api/v1/stickers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new StickerAddRequest("smile", 0.2, 1.5))));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(expectedSticker.getCategory()));

        perform.andDo(print())
                .andDo(document("add-sticker",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("category").type(JsonFieldType.STRING).description("스티커 종류"),
                                fieldWithPath("xCoordinate").type(JsonFieldType.NUMBER).description("X 좌표"),
                                fieldWithPath("yCoordinate").type(JsonFieldType.NUMBER).description("Y 좌표")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("스티커 키").optional(),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("스티커 종류"),
                                fieldWithPath("xCoordinate").type(JsonFieldType.NUMBER).description("X 좌표"),
                                fieldWithPath("yCoordinate").type(JsonFieldType.NUMBER).description("Y 좌표"))));
    }

    @Test
    @DisplayName("스티커 목록 조회")
    void stickerList() throws Exception {
        Sticker expectedSticker = new Sticker("smile", 0.2, 1.5, new Member("", new Oauth2(AuthProvider.KAKAO, "")));
        given(stickerService.findAll()).willReturn(List.of(expectedSticker));

        ResultActions perform =
                mockMvc.perform(get("/api/v1/stickers")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value(expectedSticker.getCategory()));

        perform.andDo(print())
                .andDo(document("get-sticker-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("스티커 키").optional(),
                                fieldWithPath("[].category").type(JsonFieldType.STRING).description("스티커 종류"),
                                fieldWithPath("[].xCoordinate").type(JsonFieldType.NUMBER).description("X 좌표"),
                                fieldWithPath("[].yCoordinate").type(JsonFieldType.NUMBER).description("Y 좌표"))));
    }

    @Test
    @DisplayName("스티커 삭제")
    void deleteSticker() throws Exception {
        willDoNothing().given(stickerService).deleteById(1L);

        ResultActions perform = mockMvc.perform(delete("/api/v1/stickers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("delete-sticker",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("스티커 키"))));
    }
}