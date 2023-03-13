package com.shrupp.shrupp.domain.post.dto.response;

import java.time.LocalDateTime;

public record PostResponse(String content,
                           String background,
                           LocalDateTime created,
                           LocalDateTime lastUpdated,
                           String memberNickname) {
}
