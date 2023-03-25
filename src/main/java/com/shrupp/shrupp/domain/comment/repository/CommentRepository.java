package com.shrupp.shrupp.domain.comment.repository;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPostId(Long postId);
    Long countCommentsByPostId(Long postId);
}