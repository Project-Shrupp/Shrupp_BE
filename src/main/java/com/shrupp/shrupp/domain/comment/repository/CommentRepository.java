package com.shrupp.shrupp.domain.comment.repository;

import com.shrupp.shrupp.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndMemberId(Long id, Long memberId);

    @Query("select c from Comment c left join fetch c.member where c.deleted = false and c.id = :id")
    Optional<Comment> findByIdFetchWithMember(@Param("id") Long id);

    @Query("select c from Comment c left join fetch c.member " +
            "where c.deleted = false and c.post.deleted = false and c.post.id = :postId")
    List<Comment> findByPostIdFetchWithMember(@Param("postId") Long postId);

    @Query("select c from Comment c left join fetch c.member " +
            "where c.deleted = false and c.member.deleted = false and c.id = :id and c.member.id = :memberId")
    Optional<Comment> findByIdFetchWithMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    Long countCommentsByPostId(Long postId);
}