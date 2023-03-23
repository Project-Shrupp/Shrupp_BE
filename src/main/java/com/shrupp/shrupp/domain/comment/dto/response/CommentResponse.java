package com.shrupp.shrupp.domain.comment.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(Long id,
                              String content,
                              LocalDateTime created,
                              LocalDateTime lastUpdated,
                              String memberNickname) {
}
