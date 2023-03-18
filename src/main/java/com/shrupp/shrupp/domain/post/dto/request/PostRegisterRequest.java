package com.shrupp.shrupp.domain.post.dto.request;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostRegisterRequest(@NotNull String content,
                                  @NotBlank String background,
                                  @NotNull Long memberId) {

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
