package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.domain.comment.service.CommentService;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.domain.PostReport;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostReportRequest;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest extends RestDocsTest {

    @MockBean
    private PostService postService;
    @MockBean
    private PostReportService postReportService;
    @MockBean
    private PostLikeService postLikeService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("게시글 생성")
    void registerPost() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.savePost(any(PostRegisterRequest.class), any(Long.class))).willReturn(expectedPost);

        ResultActions perform =
                mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new PostRegisterRequest("123", "#fff"))));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(expectedPost.getContent()));

        perform.andDo(print())
                .andDo(document("save-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX")),
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
        Post expectedPost = new Post("123", "#fff", new Member("", null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.findAllByPaging(any(Pageable.class))).willReturn(new PageImpl<>(List.of(expectedPost)));

        ResultActions perform =
                mockMvc.perform(get("/api/v1/posts?page=0&size=20&sort=created,desc")
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(expectedPost.getContent()));

        perform.andDo(print())
                .andDo(document("get-post-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("게시글 개수"),
                                parameterWithName("sort").description("정렬기준[,차순]")),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("게시글 키").optional(),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].backgroundColor").type(JsonFieldType.STRING).description("배경 HEX"),
                                fieldWithPath("[].created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("[].postLikeTally.count").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                                fieldWithPath("[].commentTally.count").type(JsonFieldType.NUMBER).description("댓글 개수"))));
    }

    @Test
    @DisplayName("게시글 조회")
    void getPost() throws Exception {
        Post expectedPost = new Post("123", "#fff", new Member("", null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.findById(any(Long.class))).willReturn(expectedPost);

        ResultActions perform = mockMvc.perform(get("/api/v1/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(expectedPost.getContent()));

        perform.andDo(print())
                .andDo(document("get-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키")),
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
        Post expectedPost = new Post("123", "#fff", new Member("", null));
        Field baseTimeField = Post.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedPost, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(postService.updatePost(any(Long.class), any(PostUpdateRequest.class), any(Long.class))).willReturn(expectedPost);

        ResultActions perform = mockMvc.perform(put("/api/v1/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PostUpdateRequest("123", "#fff"))));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(expectedPost.getContent()));

        perform.andDo(print())
                .andDo(document("modify-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키")),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("배경 HEX")),
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
        willDoNothing().given(postService).deletePost(1L, 1L);

        ResultActions perform = mockMvc.perform(delete("/api/v1/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("delete-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키"))));
    }

    @Test
    @DisplayName("게시글 신고")
    void reportPost() throws Exception {
        PostReport expectedPostReport = new PostReport("욕설/비하", new Post("123", "#fff", null), new Member());
        given(postReportService.report(any(Long.class), any(PostReportRequest.class), any(Long.class))).willReturn(expectedPostReport);

        ResultActions perform = mockMvc.perform(post("/api/v1/posts/{postId}/reports", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PostReportRequest("욕설/비하"))));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.reportType").value(expectedPostReport.getReportType()));

        perform.andDo(print())
                .andDo(document("report-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키")),
                        requestFields(
                                fieldWithPath("reportType").type(JsonFieldType.STRING).description("신고 타입")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("신고 키").optional(),
                                fieldWithPath("reportType").type(JsonFieldType.STRING).description("신고 타입"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일").optional(),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 키").optional(),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 키").optional())));
    }

    @Test
    @DisplayName("게시글 개수 조회")
    void postCount() throws Exception {
        given(postService.getPostCount()).willReturn(1L);

        ResultActions perform = mockMvc.perform(get("/api/v1/posts/count")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));

        perform.andDo(print())
                .andDo(document("post-count",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("게시글 개수"))));
    }

    @Test
    @DisplayName("게시글 좋아요")
    void likePost() throws Exception {
        given(postLikeService.like(any(Long.class), any(Long.class))).willReturn(true);

        ResultActions perform = mockMvc.perform(post("/api/v1/posts/{postId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("like-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키"))));
    }

    @Test
    @DisplayName("게시글 좋아요 취소")
    void unlikePost() throws Exception {
        given(postLikeService.unlike(any(Long.class), any(Long.class))).willReturn(true);

        ResultActions perform = mockMvc.perform(delete("/api/v1/posts/{postId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("unlike-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키"))));
    }

    @Test
    @DisplayName("게시글 좋아요 여부 확인")
    void checkPostLike() throws Exception {
        given(postLikeService.liked(any(Long.class), any(Long.class))).willReturn(true);

        ResultActions perform = mockMvc.perform(get("/api/v1/posts/{postId}/likes", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.liked").value(true));

        perform.andDo(print())
                .andDo(document("post-liked",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키")),
                        responseFields(
                                fieldWithPath("liked").type(JsonFieldType.BOOLEAN).description("좋아요 여부").optional())));
    }

    @Test
    @DisplayName("게시글 좋아요 개수")
    void postLikeCount() throws Exception {
        given(postLikeService.getPostLikeCount(any(Long.class))).willReturn(1L);

        ResultActions perform = mockMvc.perform(get("/api/v1/posts/{postId}/likes/count", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));

        perform.andDo(print())
                .andDo(document("post-like-count",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("게시글 키")),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("좋아요 개수"))));
    }
}