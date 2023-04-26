package com.shrupp.shrupp.domain.comment.dto.response;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(Long id,
                              String content,
                              LocalDateTime created,
                              LocalDateTime lastUpdated,
                              String memberNickname) {


    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .created(comment.getBaseTime().getCreated())
                .lastUpdated(comment.getBaseTime().getLastUpdated())
                .memberNickname(comment.getMember().getNickname())
                .build();
    }
}
