package com.shrupp.shrupp.domain.comment.dto.response;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(Long id,
                              String content,
                              LocalDateTime created,
                              LocalDateTime lastUpdated,
                              String memberNickname,
                              Boolean isWriter) {


    public static CommentResponse of(Comment comment, Boolean isWriter) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .lastUpdated(comment.getLastUpdated())
                .memberNickname(comment.getMember().getNickname())
                .isWriter(isWriter)
                .build();
    }
}
