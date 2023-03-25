package com.shrupp.shrupp.domain.comment.controller;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentReportRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentUpdateRequest;
import com.shrupp.shrupp.domain.comment.dto.response.CommentReportResponse;
import com.shrupp.shrupp.domain.comment.dto.response.CommentResponse;
import com.shrupp.shrupp.domain.comment.dto.response.CommentTallyResponse;
import com.shrupp.shrupp.domain.comment.service.CommentReportService;
import com.shrupp.shrupp.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CommentResponse>> commentList(@RequestParam Long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId).stream()
                .map(Comment::toCommentResponse)
                .toList());
    }

    @GetMapping("/count")
    public ResponseEntity<CommentTallyResponse> commentCount(@RequestParam Long postId) {
        return ResponseEntity.ok(new CommentTallyResponse(commentService.getCommentCountByPostId(postId)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> commentAdd(@RequestBody @Validated CommentRegisterRequest commentRegisterRequest) {
        return ResponseEntity.ok(commentService.addComment(commentRegisterRequest).toCommentResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> commentUpdate(@PathVariable Long id, @RequestBody @Validated CommentUpdateRequest commentUpdateRequest) {
        return ResponseEntity.ok(commentService.updateComment(id, commentUpdateRequest).toCommentResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Objects> commentDelete(@PathVariable Long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/reports")
    public ResponseEntity<CommentReportResponse> commentReport(@PathVariable Long commentId,
                                                               @RequestBody @Validated CommentReportRequest commentReportRequest) {
        return ResponseEntity.ok(commentReportService.report(commentId, commentReportRequest).toCommentReportResponse());
    }
}
