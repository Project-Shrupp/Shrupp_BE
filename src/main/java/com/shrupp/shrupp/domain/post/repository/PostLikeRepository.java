package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.entity.PostLike;
import com.shrupp.shrupp.domain.post.entity.PostLikeId;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    boolean existsPostLikeByIdPostIdAndIdMemberId(Long postId, Long memberId);

    Optional<PostLike> findPostLikeByIdPostIdAndIdMemberId(Long postId, Long memberId);

    Long countPostLikesByIdPostId(Long postId);
}
