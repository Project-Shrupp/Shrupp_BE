package com.shrupp.shrupp.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequest(@NotNull String content) {
}
