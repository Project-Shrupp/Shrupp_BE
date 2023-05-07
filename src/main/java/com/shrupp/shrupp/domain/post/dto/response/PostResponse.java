package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(String content,
                           String backgroundColor,
                           LocalDateTime created,
                           LocalDateTime lastUpdated,
                           String memberNickname,
                           Boolean isWriter) {

    public static PostResponse of(Post post, Boolean isWriter) {
        return PostResponse.builder()
                .content(post.getContent())
                .backgroundColor(post.getBackgroundColor())
                .created(post.getCreated())
                .lastUpdated(post.getLastUpdated())
                .memberNickname(post.getMember().getNickname())
                .isWriter(isWriter)
                .build();
    }
}
