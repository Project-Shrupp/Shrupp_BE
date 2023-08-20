package com.shrupp.shrupp.domain.comment.controller;

import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.comment.entity.CommentReport;
import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentReportRequest;
import com.shrupp.shrupp.domain.comment.service.CommentReportService;
import com.shrupp.shrupp.domain.comment.service.CommentService;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.support.docs.RestDocsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

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

    @Mock private Member member;
    @Mock private Comment comment;
    @MockBean private CommentService commentService;
    @MockBean private CommentReportService commentReportService;

    @BeforeEach
    void initMock() {
        given(member.getId()).willReturn(1L);
        given(member.getNickname()).willReturn("member");

        given(comment.getId()).willReturn(1L);
        given(comment.getContent()).willReturn("test");
        given(comment.getCreated()).willReturn(LocalDateTime.now());
        given(comment.getLastUpdated()).willReturn(LocalDateTime.now());
        given(comment.getMember()).willReturn(member);
    }

    @Test
    @DisplayName("댓글 생성")
    void registerComment() throws Exception {
        given(commentService.addComment(any(CommentRegisterRequest.class), anyLong())).willReturn(comment);

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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 키"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                                fieldWithPath("isWriter").type(JsonFieldType.BOOLEAN).description("작성자 여부"))));
    }

    @Test
    @DisplayName("댓글 리스트 조회")
    void getCommentList() throws Exception {
        given(commentService.findCommentsByPostId(any(Long.class))).willReturn(List.of(comment));

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
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("댓글 키"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("[].lastUpdated").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("[].memberNickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                                fieldWithPath("[].isWriter").type(JsonFieldType.BOOLEAN).description("작성자 여부"))));
    }

    @Test
    @DisplayName("댓글 개수 조회")
    void getCommentCount() throws Exception {
        Long commentCount = 1L;
        given(commentService.getCommentCountByPostId(any(Long.class))).willReturn(commentCount);

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
        given(comment.isDeleted()).willReturn(true);
        given(commentService.deleteComment(any(Long.class), any(Long.class))).willReturn(comment);

        ResultActions perform = mockMvc.perform(delete("/api/v1/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isNoContent());

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
        CommentReport commentReport = mock(CommentReport.class);
        given(commentReport.getReportType()).willReturn("욕설/비하");
        given(commentReport.getCreated()).willReturn(LocalDateTime.now());
        given(commentReport.getComment()).willReturn(comment);
        given(commentReport.getMember()).willReturn(member);
        given(commentReportService.report(any(Long.class), any(CommentReportRequest.class), any(Long.class))).willReturn(commentReport);

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
                                fieldWithPath("created").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 키"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("신고자 키"))));
    }
}