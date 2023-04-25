package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Slice<Post> findAllByPaging(Pageable pageable) {
        return postRepository.findPagingAll(pageable);
    }

    public Post findById(Long postId) {
        return postRepository.findByIdWithFetch(postId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Post savePost(PostRegisterRequest postRegisterRequest, Long memberId) {
        return postRepository.save(postRegisterRequest.toPostEntity(memberService.findById(memberId)));
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest, Long memberId) {
        Post post = postRepository.findByIdWithFetchMemberId(postId, memberId)
                .orElseThrow(EntityNotFoundException::new);

        post.updatePost(postUpdateRequest.content(), postUpdateRequest.backgroundColor());
        return post;
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) {
        postRepository.deleteByIdAndMemberId(postId, memberId);
    }

    public Long getPostCount() {
        return postRepository.count();
    }
}
