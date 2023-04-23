package com.shrupp.shrupp.domain.comment.repository;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.member where c.id = :id")
    Optional<Comment> findByIdFetchWithMember(Long id);

    @Query("select c from Comment c left join fetch c.member where c.post.id = :postId")
    List<Comment> findByPostIdFetchWithMember(Long postId);

    Long countCommentsByPostId(Long postId);
}