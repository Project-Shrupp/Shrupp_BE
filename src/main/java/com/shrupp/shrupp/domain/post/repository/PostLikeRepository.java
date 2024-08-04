package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Optional<PostLike> findPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    Long countPostLikesByPostId(Long postId);

    void deleteByPostId(Long postId);
}
