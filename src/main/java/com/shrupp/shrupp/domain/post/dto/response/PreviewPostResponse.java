package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.comment.dto.response.CommentTallyResponse;
import com.shrupp.shrupp.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PreviewPostResponse(Long id,
                                  String content,
                                  String backgroundColor,
                                  LocalDateTime created,
                                  Boolean isWriter,
                                  PostLikeTallyResponse postLikeTally,
                                  CommentTallyResponse commentTally,
                                  Long totalCount,
                                  Boolean isLastPage) {

    public static PreviewPostResponse of(Post post, Boolean isWriter,
                                         PostLikeTallyResponse postLikeTally, CommentTallyResponse commentTally,
                                         Long totalCount, Boolean isLastPage) {
        return PreviewPostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .backgroundColor(post.getBackgroundColor())
                .created(post.getCreated())
                .isWriter(isWriter)
                .postLikeTally(postLikeTally)
                .commentTally(commentTally)
                .totalCount(totalCount)
                .isLastPage(isLastPage)
                .build();
    }
}
