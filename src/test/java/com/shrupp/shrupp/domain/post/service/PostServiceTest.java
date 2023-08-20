package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.comment.repository.CommentRepository;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.repository.PostLikeRepository;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock PostRepository postRepository;
    @Mock PostLikeRepository postLikeRepository;
    @Mock CommentRepository commentRepository;
    @Mock MemberService memberService;
    @InjectMocks PostService postService;

    @Test
    @DisplayName("페이징하여 게시글 여러 개 조회할 수 있다")
    void findAllPostByPaging() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("content", "#ffffff", member);
        given(postRepository.findPagingAll(any(Pageable.class))).willReturn(new PageImpl<>(List.of(post), PageRequest.of(0, 10), 1));

        Page<Post> posts = postService.findAllByPaging(PageRequest.of(0, 10));

        assertThat(posts).hasSize(1);
        assertThat(posts.getTotalPages()).isEqualTo(1);
        assertThat(posts.isLast()).isTrue();
    }

    @Test
    @DisplayName("게시글 id로 게시글을 조회할 수 있다")
    void findPostByPostId() {
        long postId = 1L;
        Member member = new Member("", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("content", "#ffffff", member);
        given(postRepository.findByIdWithFetch(any(Long.class))).willReturn(Optional.of(post));

        Post findPost = postService.findById(postId);

        assertThat(findPost).isEqualTo(post);
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다")
    void savePost() {
        long memberId = 1L;
        PostRegisterRequest request = new PostRegisterRequest("content", "#ffffff");
        Member member = new Member("", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("content", "#ffffff", member);
        given(postRepository.save(any(Post.class))).willReturn(post);

        Post savedPost = postService.savePost(request, memberId);

        assertThat(savedPost).isEqualTo(post);
    }

    @Test
    @DisplayName("게시글에서 내용과 배경색을 수정할 수 있다")
    void updatePost() {
        long postId = 1L;
        long memberId = 1L;
        PostUpdateRequest request = new PostUpdateRequest("update content", "#000000");
        Member member = new Member("", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("content", "#ffffff", member);
        given(postRepository.findByIdAndMemberIdWithFetchMember(any(Long.class), any(Long.class))).willReturn(Optional.of(post));

        Post updatedPost = postService.updatePost(postId, request, memberId);

        assertThat(updatedPost.getContent()).isEqualTo(request.content());
        assertThat(updatedPost.getBackgroundColor()).isEqualTo(request.backgroundColor());
    }

    @Test
    @DisplayName("게시글을 삭제할 수 있다")
    void deletePost() {
        long postId = 1L;
        long memberId = 1L;
        Member member = new Member("", new Oauth2(AuthProvider.KAKAO, ""));
        Post post = new Post("content", "#ffffff", member);
        given(postRepository.findByIdAndMemberIdWithFetchMember(any(Long.class), any(Long.class)))
                .willReturn(Optional.of(post));

        Post deletedPost = postService.deletePost(postId, memberId);

        assertThat(deletedPost.getDeleted()).isTrue();
    }

    @Test
    @DisplayName("전체 게시글 개수를 가져올 수 있다")
    void getPostCount() {
        long postCount = 10L;
        given(postRepository.count()).willReturn(postCount);

        long findPostCount = postService.getPostCount();

        assertThat(findPostCount).isEqualTo(postCount);
    }
}