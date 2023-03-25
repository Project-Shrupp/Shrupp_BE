package com.shrupp.shrupp.domain.post.dto.response;

import jakarta.validation.constraints.NotNull;

public record StickerResponse(@NotNull String category,
                              @NotNull Double xCoordinate,
                              @NotNull Double yCoordinate,
                              @NotNull Long memberId) {
}
