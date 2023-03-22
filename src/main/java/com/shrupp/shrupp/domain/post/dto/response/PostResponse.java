package com.shrupp.shrupp.domain.post.dto.response;

import java.time.LocalDateTime;

public record PostResponse(String content,
                           String backgroundColor,
                           LocalDateTime created,
                           LocalDateTime lastUpdated,
                           String memberNickname) {
}
