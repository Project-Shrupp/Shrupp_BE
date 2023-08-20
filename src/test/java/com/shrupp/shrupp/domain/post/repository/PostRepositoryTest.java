package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import com.shrupp.shrupp.domain.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    @DisplayName("게시글을 페이징하여 조회할 수 있다")
    void findPagingAll() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post1 = new Post("content1", "#ffffff", member);
        Post post2 = new Post("content2", "#ffffff", member);
        Post post3 = new Post("content3", "#ffffff", member);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Page<Post> findPosts = postRepository.findPagingAll(PageRequest.of(0, 2));

        assertThat(findPosts).hasSize(2);
        assertThat(findPosts).containsExactly(post1, post2);
    }

    @Test
    @DisplayName("게시글 id로 특정 게시글을 조회할 수 있다")
    void findByPostId() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);

        Optional<Post> findPost = postRepository.findByIdWithFetch(post.getId());

        assertThat(findPost).isNotEmpty();
        assertThat(findPost).get().isEqualTo(post);
    }

    @Test
    @DisplayName("게시글 id와 멤버 id로 특정 게시글을 조회할 수 있다")
    void findByPostIdAndMemberId() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);

        Optional<Post> findPost = postRepository.findByIdAndMemberIdWithFetchMember(post.getId(), member.getId());

        assertThat(findPost).isNotEmpty();
        assertThat(findPost).get().isEqualTo(post);
    }
}