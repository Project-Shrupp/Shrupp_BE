package com.shrupp.shrupp.domain.comment.controller;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.comment.domain.CommentReport;
import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentReportRequest;
import com.shrupp.shrupp.domain.comment.service.CommentReportService;
import com.shrupp.shrupp.domain.comment.service.CommentService;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
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
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends RestDocsTest {

    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentReportService commentReportService;

    @Test
    @DisplayName("댓글 생성")
    void registerComment() throws Exception {
        Member expectMember = new Member("", null);
        Field memberId = Member.class.getDeclaredField("id");
        memberId.setAccessible(true);
        memberId.set(expectMember, 1L);
        Comment expectedComment = Comment.builder()
                .content("123")
                .post(new Post("123", "#fff", expectMember))
                .member(new Member("member", null))
                .build();
        Field baseTimeField = Comment.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedComment, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(commentService.addComment(any(CommentRegisterRequest.class), any(Long.class))).willReturn(expectedComment);

        ResultActions perform = mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new CommentRegisterRequest("123", 1L))));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("save-comment",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 키")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 키").optional(),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                                fieldWithPath("isWriter").type(JsonFieldType.BOOLEAN).description("작성자 여부"))));
    }

    @Test
    @DisplayName("댓글 리스트 조회")
    void getCommentList() throws Exception {
        Member expectMember = new Member("", null);
        Field memberId = Member.class.getDeclaredField("id");
        memberId.setAccessible(true);
        memberId.set(expectMember, 1L);
        Comment expectedComment = Comment.builder()
                .content("123")
                .post(new Post("123", "#fff", expectMember))
                .member(expectMember)
                .build();
        Field baseTimeField = Comment.class.getDeclaredField("baseTime");
        baseTimeField.setAccessible(true);
        baseTimeField.set(expectedComment, new BaseTime(LocalDateTime.now(), LocalDateTime.now()));
        given(commentService.findCommentsByPostId(any(Long.class))).willReturn(List.of(expectedComment));

        ResultActions perform = mockMvc.perform(get("/api/v1/comments?postId=1")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get-comment-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("postId").description("게시글 키")),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("댓글 키").optional(),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("[].lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("[].memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                                fieldWithPath("[].isWriter").type(JsonFieldType.BOOLEAN).description("작성자 여부"))));
    }

    @Test
    @DisplayName("댓글 개수 조회")
    void getCommentCount() throws Exception {
        Long expectedCommentCount = 1L;
        given(commentService.getCommentCountByPostId(any(Long.class))).willReturn(expectedCommentCount);

        ResultActions perform = mockMvc.perform(get("/api/v1/comments/count?postId=1")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get-comment-count",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("postId").description("게시글 키")),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("댓글 개수"))));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        willDoNothing().given(commentService).deleteComment(1L, 1L);

        ResultActions perform = mockMvc.perform(delete("/api/v1/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("delete-comment",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                           parameterWithName("id").description("댓글 키"))));
    }

    @Test
    @DisplayName("댓글 신고")
    void reportComment() throws Exception {
        Member member = new Member();
        CommentReport expectedCommentReport = new CommentReport("욕설/비하", new Comment("123", null, null), member);
        given(commentReportService.report(any(Long.class), any(CommentReportRequest.class), any(Long.class))).willReturn(expectedCommentReport);

        ResultActions perform = mockMvc.perform(post("/api/v1/comments/{commentId}/reports", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new CommentReportRequest("욕설/비하"))));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("report-comment",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 키")),
                        requestFields(
                                fieldWithPath("reportType").type(JsonFieldType.STRING).description("신고 타입")),
                        responseFields(
                                fieldWithPath("reportType").type(JsonFieldType.STRING).description("신고 타입"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일").optional(),
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 키").optional(),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("신고자 키").optional())));
    }
}