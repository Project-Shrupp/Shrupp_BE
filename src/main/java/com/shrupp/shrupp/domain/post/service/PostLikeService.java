package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.domain.PostLike;
import com.shrupp.shrupp.domain.post.dto.request.PostLikeRequest;
import com.shrupp.shrupp.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final MemberService memberService;

    @Transactional
    public boolean like(Long postId, PostLikeRequest postLikeRequest) {
        Post post = postService.findById(postId);
        Member member = memberService.findById(postLikeRequest.memberId());

        if (postLikeRepository.findPostLikeByPostAndMember(post, member).isPresent()) {
            return false;
        }

        postLikeRepository.save(new PostLike(post, member));
        return true;
    }

    @Transactional
    public boolean unlike(Long postId, PostLikeRequest postLikeRequest) {
        Post post = postService.findById(postId);
        Member member = memberService.findById(postLikeRequest.memberId());

        Optional<PostLike> postLike = postLikeRepository.findPostLikeByPostAndMember(post, member);
        if (postLike.isEmpty()) {
            return false;
        }

        postLikeRepository.delete(postLike.get());
        return true;
    }

    public Long getPostLikeCount(Long postId) {
        return postLikeRepository.countPostLikesByPostId(postId);
    }
}
