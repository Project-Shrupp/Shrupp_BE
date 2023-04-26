package com.shrupp.shrupp.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(@NotNull String content,
                                @NotBlank String backgroundColor) {
}
