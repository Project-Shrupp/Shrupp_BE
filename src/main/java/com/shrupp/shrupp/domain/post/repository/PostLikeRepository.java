package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Optional<PostLike> findPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Long countPostLikesByPostId(Long postId);
}
