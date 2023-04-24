package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostReportRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.dto.response.*;
import com.shrupp.shrupp.domain.post.service.PostLikeService;
import com.shrupp.shrupp.domain.post.service.PostReportService;
import com.shrupp.shrupp.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostReportService postReportService;
    private final PostLikeService postLikeService;

    @GetMapping
    public ResponseEntity<List<SimplePostResponse>> postList() {
        return ResponseEntity.ok(postService.findAll().stream()
                .map(SimplePostResponse::of)
                .toList());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> postDetails(@PathVariable Long postId) {
        return ResponseEntity.ok(PostResponse.of(postService.findById(postId)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> postAdd(@RequestBody @Validated PostRegisterRequest postRegisterRequest,
                                                @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(PostResponse.of(postService.savePost(postRegisterRequest, loginUser.getMember().getId())));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> postModify(@PathVariable Long postId,
                                                   @RequestBody @Validated PostUpdateRequest postUpdateRequest,
                                                   @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(PostResponse.of(postService.updatePost(postId, postUpdateRequest, loginUser.getMember().getId())));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Objects> postRemove(@PathVariable Long postId,
                                              @AuthenticationPrincipal LoginUser loginUser) {
        postService.deletePost(postId, loginUser.getMember().getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/reports")
    public ResponseEntity<PostReportResponse> postReport(@PathVariable Long postId,
                                                         @RequestBody @Validated PostReportRequest postReportRequest,
                                                         @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(PostReportResponse.of(postReportService.report(postId, postReportRequest, loginUser.getMember().getId())));
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<PostLikeResponse> getPostLike(@PathVariable Long postId,
                                                        @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(new PostLikeResponse(postLikeService.liked(postId, loginUser.getMember().getId())));
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Objects> postLike(@PathVariable Long postId,
                                            @AuthenticationPrincipal LoginUser loginUser) {
        if (postLikeService.like(postId, loginUser.getMember().getId())) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Objects> postUnlike(@PathVariable Long postId,
                                              @AuthenticationPrincipal LoginUser loginUser) {
        if (postLikeService.unlike(postId, loginUser.getMember().getId())) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{postId}/likes/count")
    public ResponseEntity<PostLikeTallyResponse> postLikeCount(@PathVariable Long postId) {
        return ResponseEntity.ok(new PostLikeTallyResponse(postLikeService.getPostLikeCount(postId)));
    }
}
