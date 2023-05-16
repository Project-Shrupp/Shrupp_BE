package com.shrupp.shrupp.domain.post.dto.request;

import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRegisterRequest(@NotNull String content,
                                  @NotBlank String backgroundColor) {

    public Post toPostEntity(Member member) {
        return Post.builder()
                .content(content)
                .backgroundColor(backgroundColor)
                .member(member)
                .build();
    }
}
