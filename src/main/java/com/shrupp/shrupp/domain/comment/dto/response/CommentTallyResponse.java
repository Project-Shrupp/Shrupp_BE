package com.shrupp.shrupp.domain.comment.dto.response;

import jakarta.validation.constraints.NotNull;

public record CommentTallyResponse(@NotNull Long count) {
}
