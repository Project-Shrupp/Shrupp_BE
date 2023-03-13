package com.shrupp.shrupp.domain.post.dto.request;

public record PostUpdateRequest(String content,
                                String background,
                                Long memberId) {
}
