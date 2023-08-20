package com.shrupp.shrupp.domain.comment.service;

import com.shrupp.shrupp.domain.comment.dto.request.CommentRegisterRequest;
import com.shrupp.shrupp.domain.comment.dto.request.CommentUpdateRequest;
import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.comment.repository.CommentRepository;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock Comment comment;
    @Mock CommentRepository commentRepository;
    @Mock PostService postService;
    @Mock MemberService memberService;
    @InjectMocks CommentService commentService;

    @Test
    @DisplayName("id를 통해 댓글을 조회할 수 있다")
    public void findCommentById() {
        long id = 1L;
        given(commentRepository.findByIdFetchWithMember(any(Long.class))).willReturn(Optional.ofNullable(comment));

        Comment findComment = commentService.findById(id);

        assertThat(findComment).isEqualTo(this.comment);
    }

    @Test
    @DisplayName("게시글 id를 통해 댓글을 조회할 수 있다")
    public void findCommentsByPostId() {
        long postId = 1L;
        given(commentRepository.findByPostIdFetchWithMember(any(Long.class))).willReturn(List.of(comment));

        List<Comment> findComments = commentService.findCommentsByPostId(postId);

        assertThat(findComments.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글을 추가할 수 있다")
    public void addComment() {
        Member member = new Member();
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(postService.findById(any(Long.class))).willReturn(new Post("", "", member));
        given(memberService.findById(any(Long.class))).willReturn(member);

        Comment savedComment = commentService.addComment(new CommentRegisterRequest("content", 1L), 1L);

        assertThat(savedComment).isEqualTo(comment);
    }

    @Test
    @DisplayName("댓글을 수정할 수 있다")
    public void updateComment() {
        long id = 1L;
        long memberId = 1L;
        given(commentRepository.findByIdAndMemberIdFetchWithMember(any(Long.class), any(Long.class))).willReturn(Optional.ofNullable(comment));

        Comment updatedComment = commentService.updateComment(id, new CommentUpdateRequest("content"), memberId);

        assertThat(updatedComment).isEqualTo(comment);
    }

    @Test
    @DisplayName("댓글을 삭제할 수 있다")
    public void deleteComment() {
        given(comment.isDeleted()).willReturn(true);
        given(commentRepository.findByIdAndMemberIdFetchWithMember(any(Long.class), any(Long.class))).willReturn(Optional.ofNullable(comment));
        Comment comment = commentService.deleteComment(1L, 1L);

        assertThat(comment.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("게시글 id를 통해 댓글 개수 조회")
    public void getCommentCountByPostId() {
        long postId = 1L;
        given(commentRepository.countCommentsByPostId(any(Long.class))).willReturn(1L);

        Long commentCount = commentService.getCommentCountByPostId(postId);

        assertThat(commentCount).isEqualTo(1L);
    }
}