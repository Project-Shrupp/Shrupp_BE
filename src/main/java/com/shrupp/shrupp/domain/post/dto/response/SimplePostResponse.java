package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.comment.dto.response.CommentTallyResponse;
import com.shrupp.shrupp.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SimplePostResponse(Long id,
                                 String content,
                                 String backgroundColor,
                                 LocalDateTime created,
                                 PostLikeTallyResponse postLikeTally,
                                 CommentTallyResponse commentTally) {

    public static SimplePostResponse of(Post post, PostLikeTallyResponse postLikeTally, CommentTallyResponse commentTally) {
        return SimplePostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .backgroundColor(post.getBackgroundColor())
                .created(post.getBaseTime().getCreated())
                .postLikeTally(postLikeTally)
                .commentTally(commentTally)
                .build();
    }
}
