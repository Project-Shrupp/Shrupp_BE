package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.domain.comment.dto.response.CommentTallyResponse;
import com.shrupp.shrupp.domain.comment.service.CommentService;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostReportRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.dto.response.*;
import com.shrupp.shrupp.domain.post.service.PostLikeService;
import com.shrupp.shrupp.domain.post.service.PostReportService;
import com.shrupp.shrupp.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<PreviewPostResponse>> postList(@PageableDefault(
                                                              page = 0,
                                                              size = 20,
                                                              sort = "created",
                                                              direction = Sort.Direction.DESC) Pageable pageable,
                                                              @AuthenticationPrincipal LoginUser loginUser) {

        Page<Post> postsByPaging = postService.findAllByPaging(pageable);
        return ResponseEntity.ok(postsByPaging.stream()
                .map(post ->
                        PreviewPostResponse.of(post, isWriter(loginUser, post),
                        new PostLikeTallyResponse(postLikeService.getPostLikeCount(post.getId())),
                        new CommentTallyResponse(commentService.getCommentCountByPostId(post.getId())),
                                postsByPaging.getTotalElements(),
                                postsByPaging.isLast()))
                .toList());
    }

    @GetMapping("/count")
    public ResponseEntity<PostTallyResponse> postCount() {
        return ResponseEntity.ok(new PostTallyResponse(postService.getPostCount()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> postDetails(@PathVariable Long postId,
                                                    @AuthenticationPrincipal LoginUser loginUser) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(PostResponse.of(post, isWriter(loginUser, post)));
    }

    private static boolean isWriter(LoginUser loginUser, Post post) {
        return post.getMember().getId().equals(loginUser.getMember().getId());
    }

    @PostMapping
    public ResponseEntity<PostResponse> postAdd(@RequestBody @Validated PostRegisterRequest postRegisterRequest,
                                                @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(PostResponse.of(postService.savePost(postRegisterRequest, loginUser.getMember().getId()), true));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> postModify(@PathVariable Long postId,
                                                   @RequestBody @Validated PostUpdateRequest postUpdateRequest,
                                                   @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(PostResponse.of(postService.updatePost(postId, postUpdateRequest, loginUser.getMember().getId()), true));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Objects> postRemove(@PathVariable Long postId,
                                              @AuthenticationPrincipal LoginUser loginUser) {
        Post post = postService.deletePost(postId, loginUser.getMember().getId());
        if (post.isDeleted()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
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
