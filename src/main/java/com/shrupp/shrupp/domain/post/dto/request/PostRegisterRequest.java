package com.shrupp.shrupp.domain.post.dto.request;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;

import java.time.LocalDateTime;

public record PostRegisterRequest(String content,
                                  String background,
                                  Long memberId) {

    public Post toPostEntity(Member member) {
        return Post.builder()
                .content(content)
                .background(background)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .member(member)
                .build();
    }
}
