package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    MemberService memberService;
    @InjectMocks
    PostService postService;

    @Test
    @DisplayName("페이징하여 게시글 여러 개 조회")
    void findAllPostByPaging() {
        Member member = new Member("", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("", "", member);
        given(postRepository.findPagingAll(any(Pageable.class))).willReturn(new PageImpl<>(List.of(post), PageRequest.of(0, 10), 1));

        Page<Post> posts = postService.findAllByPaging(PageRequest.of(0, 10));

        assertThat(posts).hasSize(1);
        assertThat(posts.getTotalPages()).isEqualTo(1);
        assertThat(posts.isLast()).isTrue();
    }

}