package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.dto.request.PostRegisterRequest;
import com.shrupp.shrupp.domain.post.dto.request.PostUpdateRequest;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Post save(PostRegisterRequest postRegisterRequest) {
        return postRepository.save(postRegisterRequest.toPostEntity(memberService.findById(postRegisterRequest.memberId())));
    }

    public Post update(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = findById(id);
        post.updatePost(postUpdateRequest.content(), postUpdateRequest.background());

        return post;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
