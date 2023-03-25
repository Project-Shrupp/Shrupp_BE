package com.shrupp.shrupp.domain.comment.service;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentUpdateRequest;
import com.shrupp.shrupp.domain.comment.repository.CommentRepository;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    public Long getCommentCountByPostId(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }

    public Comment addComment(CommentRegisterRequest commentRegisterRequest) {
        return commentRepository.save(commentRegisterRequest.toCommentEntity(
                postService.findById(commentRegisterRequest.postId()),
                memberService.findById(commentRegisterRequest.memberId())));
    }

    @Transactional
    public Comment updateComment(Long id, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = findById(id);
        comment.updateComment(commentUpdateRequest.content());

        return comment;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
