package com.shrupp.shrupp.domain.comment.controller;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.comment.dto.request.CommentReportRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentUpdateRequest;
import com.shrupp.shrupp.domain.comment.dto.response.CommentReportResponse;
import com.shrupp.shrupp.domain.comment.dto.response.CommentResponse;
import com.shrupp.shrupp.domain.comment.dto.response.CommentTallyResponse;
import com.shrupp.shrupp.domain.comment.service.CommentReportService;
import com.shrupp.shrupp.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentReportService commentReportService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> commentList(@RequestParam Long postId,
                                                             @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId).stream()
                .map(comment -> CommentResponse.of(comment, isWriter(loginUser, comment)))
                .toList());
    }

    private static boolean isWriter(LoginUser loginUser, Comment comment) {
        return comment.getMember().getId().equals(loginUser.getMember().getId());
    }

    @GetMapping("/count")
    public ResponseEntity<CommentTallyResponse> commentCount(@RequestParam Long postId) {
        return ResponseEntity.ok(new CommentTallyResponse(commentService.getCommentCountByPostId(postId)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> commentAdd(@RequestBody @Validated CommentRegisterRequest commentRegisterRequest,
                                                      @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(CommentResponse.of(commentService.addComment(commentRegisterRequest, loginUser.getMember().getId()), true));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> commentUpdate(@PathVariable Long commentId,
                                                         @RequestBody @Validated CommentUpdateRequest commentUpdateRequest,
                                                         @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(CommentResponse.of(commentService.updateComment(commentId, commentUpdateRequest, loginUser.getMember().getId()), true));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Objects> commentDelete(@PathVariable Long commentId,
                                                 @AuthenticationPrincipal LoginUser loginUser) {
        commentService.deleteComment(commentId, loginUser.getMember().getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/reports")
    public ResponseEntity<CommentReportResponse> commentReport(@PathVariable Long commentId,
                                                               @RequestBody @Validated CommentReportRequest commentReportRequest,
                                                               @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(CommentReportResponse.of(commentReportService.report(commentId, commentReportRequest, loginUser.getMember().getId())));
    }
}
