package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.domain.PostLike;
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
    public boolean like(Long postId, Long memberId) {
        if (postLikeRepository.existsPostLikeByPostIdAndMemberId(postId, memberId)) {
            return false;
        }

        Post post = postService.findById(postId);
        Member member = memberService.findById(memberId);

        postLikeRepository.save(new PostLike(post, member));
        return true;
    }

    @Transactional
    public boolean unlike(Long postId, Long memberId) {
        Optional<PostLike> postLike = postLikeRepository.findPostLikeByPostIdAndMemberId(postId, memberId);
        if (postLike.isEmpty()) {
            return false;
        }

        postLikeRepository.delete(postLike.get());
        return true;
    }

    public boolean liked(Long postId, Long memberId) {
        return postLikeRepository.existsPostLikeByPostIdAndMemberId(postId, memberId);
    }

    public Long getPostLikeCount(Long postId) {
        return postLikeRepository.countPostLikesByPostId(postId);
    }
}
