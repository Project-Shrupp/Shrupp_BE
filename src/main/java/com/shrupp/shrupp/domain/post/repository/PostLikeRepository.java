package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Optional<PostLike> findPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Long countPostLikesByPostId(Long postId);
}
