package com.shrupp.shrupp.domain.comment.repository;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;

    @Test
    @DisplayName("게시글 id로 특정 게시글의 댓글 목록을 조회할 수 있다")
    void findByPostId() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Comment comment1 = new Comment("content1", post, member);
        Comment comment2 = new Comment("content2", post, member);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> findComments = commentRepository.findByPostIdFetchWithMember(post.getId());

        assertThat(findComments).hasSize(2);
        assertThat(findComments).containsExactly(comment1, comment2);
    }

    @Test
    @DisplayName("댓글 id로 특정 댓글을 조회할 수 있다")
    void findById() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Comment comment = new Comment("content1", post, member);
        commentRepository.save(comment);

        Optional<Comment> findComment = commentRepository.findByIdFetchWithMember(comment.getId());

        assertThat(findComment).isNotEmpty();
        assertThat(findComment).get().isEqualTo(comment);
    }

    @Test
    @DisplayName("댓글 id와 멤버 id로 특정 댓글을 조회할 수 있다")
    void findByPostIdAndMemberId() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Comment comment = new Comment("content1", post, member);
        commentRepository.save(comment);

        Optional<Comment> findComment = commentRepository
                .findByIdAndMemberIdFetchWithMember(comment.getId(), member.getId());

        assertThat(findComment).isNotEmpty();
        assertThat(findComment).get().isEqualTo(comment);
    }

    @Test
    @DisplayName("게시글 id로 특정 게시글의 댓글 수를 조회할 수 있다")
    void countCommentsByPostId() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Comment comment1 = new Comment("content1", post, member);
        Comment comment2 = new Comment("content2", post, member);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        Long commentCount = commentRepository.countCommentsByPostId(post.getId());

        assertThat(commentCount).isEqualTo(2L);
    }
}