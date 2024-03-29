package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.comment.repository.CommentRepository;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.repository.PostLikeRepository;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;

    public Page<Post> findAllByPaging(Pageable pageable) {
        return postRepository.findPagingAll(pageable);
    }

    public Post findById(Long postId) {
        return postRepository.findByIdWithFetch(postId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Post savePost(PostRegisterRequest postRegisterRequest, Long memberId) {
        return postRepository.save(postRegisterRequest.toPostEntity(memberService.findById(memberId)));
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest, Long memberId) {
        Post post = postRepository.findByIdAndMemberIdWithFetchMember(postId, memberId)
                .orElseThrow(EntityNotFoundException::new);

        post.updatePost(postUpdateRequest.content(), postUpdateRequest.backgroundColor());
        return post;
    }

    @Transactional
    public Post deletePost(Long postId, Long memberId) {
        Post post = postRepository.findByIdAndMemberIdWithFetchMember(postId, memberId)
                .orElseThrow(EntityNotFoundException::new);

        postLikeRepository.deleteByIdPostId(post.getId());
        deleteCommentsByPostId(post.getId());
        post.delete();
        return post;
    }

    private void deleteCommentsByPostId(Long postId) {
        commentRepository.findByPostIdFetchWithMember(postId).forEach(Comment::delete);
    }

    public Long getPostCount() {
        return postRepository.count();
    }
}
