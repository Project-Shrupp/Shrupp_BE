package com.shrupp.shrupp.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;

public record PostLikeRequest(@NotNull Long memberId) {
}
