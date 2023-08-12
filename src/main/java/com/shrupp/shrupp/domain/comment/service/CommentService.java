package com.shrupp.shrupp.domain.comment.service;

import com.shrupp.shrupp.domain.comment.entity.Comment;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public Comment findById(Long id) {
        return commentRepository.findByIdFetchWithMember(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdFetchWithMember(postId);
    }

    public Long getCommentCountByPostId(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }

    @Transactional
    public Comment addComment(CommentRegisterRequest commentRegisterRequest, Long memberId) {
        return commentRepository.save(commentRegisterRequest.toCommentEntity(
                postService.findById(commentRegisterRequest.postId()),
                memberService.findById(memberId)));
    }

    @Transactional
    public Comment updateComment(Long id, CommentUpdateRequest commentUpdateRequest, Long memberId) {
        Comment comment = commentRepository.findByIdFetchWithMemberId(id, memberId)
                .orElseThrow(EntityNotFoundException::new);

        comment.updateComment(commentUpdateRequest.content());

        return comment;
    }

    @Transactional
    public Comment deleteComment(Long id, Long memberId) {
        Comment comment = commentRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(EntityNotFoundException::new);
        comment.delete();
        return comment;
    }
}
