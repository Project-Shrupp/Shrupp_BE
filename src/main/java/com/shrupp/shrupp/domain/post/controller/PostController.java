package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostLikeRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.dto.response.PostResponse;
import com.shrupp.shrupp.domain.post.dto.response.SimplePostResponse;
import com.shrupp.shrupp.domain.post.service.PostLikeService;
import com.shrupp.shrupp.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @GetMapping
    public ResponseEntity<List<SimplePostResponse>> postList() {
        return ResponseEntity.ok(postService.findAll().stream()
                .map(Post::toSimplePostResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> postDetails(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id).toPostResponse());
    }

    @PostMapping
    public ResponseEntity<PostResponse> postAdd(@RequestBody @Validated PostRegisterRequest postRegisterRequest) {
        return ResponseEntity.ok(postService.save(postRegisterRequest).toPostResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> postModify(@PathVariable Long id, @RequestBody @Validated PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok(postService.update(id, postUpdateRequest).toPostResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Objects> postRemove(@PathVariable Long id) {
        postService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Objects> postLike(@PathVariable Long postId, @RequestBody @Validated PostLikeRequest postLikeRequest) {
        if (postLikeService.like(postId, postLikeRequest)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<String> postUnlike(@PathVariable Long postId, @RequestBody @Validated PostLikeRequest postLikeRequest) {
        if (postLikeService.unlike(postId, postLikeRequest)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
