package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(String content,
                           String backgroundColor,
                           LocalDateTime created,
                           LocalDateTime lastUpdated,
                           String memberNickname) {

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .content(post.getContent())
                .backgroundColor(post.getBackgroundColor())
                .created(post.getBaseTime().getCreated())
                .lastUpdated(post.getBaseTime().getLastUpdated())
                .memberNickname(post.getMember().getNickname())
                .build();
    }
}
