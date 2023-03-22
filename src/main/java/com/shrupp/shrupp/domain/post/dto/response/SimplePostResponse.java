package com.shrupp.shrupp.domain.post.dto.response;

import java.time.LocalDateTime;

public record SimplePostResponse(Long id,
                                 String content,
                                 String backgroundColor,
                                 LocalDateTime created) {
}
