package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.service.PostLikeService;
import com.shrupp.shrupp.domain.post.service.PostReportService;
import com.shrupp.shrupp.domain.post.service.PostService;
import com.shrupp.shrupp.global.audit.BaseTime;
import com.shrupp.shrupp.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest extends RestDocsTest {

    @MockBean
    private PostService postService;
    @MockBean
    private PostReportService postReportService;
    @MockBean
    private PostLikeService postLikeService;

    @Test
    @DisplayName("게시글 생성")
    void registerPost() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", LocalDateTime.now(), LocalDateTime.now(), null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.save(any(PostRegisterRequest.class))).willReturn(expectedPost);

        ResultActions perform =
                mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new PostRegisterRequest("123", "#fff", 1L))));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("save-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 키")),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"))));
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void getPostList() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", LocalDateTime.now(), LocalDateTime.now(), null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.findAll()).willReturn(List.of(expectedPost));

        ResultActions perform =
                mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get-post-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("게시글 키").optional(),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("[].created").type(JsonFieldType.STRING).description("생성일"))));
    }

    @Test
    @DisplayName("게시글 조회")
    void getPost() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", LocalDateTime.now(), LocalDateTime.now(), null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.findById(any(Long.class))).willReturn(expectedPost);

        ResultActions perform = mockMvc.perform(get("/api/v1/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("게시글 키")),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"))));
    }

    @Test
    @DisplayName("게시글 수정")
    void modifyPost() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", LocalDateTime.now(), LocalDateTime.now(), null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.update(any(Long.class), any(PostUpdateRequest.class))).willReturn(expectedPost);

        ResultActions perform = mockMvc.perform(put("/api/v1/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PostUpdateRequest("111", "#fff", 1L))));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("modify-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("게시글 키")),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 키")),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"))));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        willDoNothing().given(postService).delete(1L);

        ResultActions perform = mockMvc.perform(delete("/api/v1/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("delete-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("게시글 키"))));
    }
}