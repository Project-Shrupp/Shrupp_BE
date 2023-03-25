package com.shrupp.shrupp.domain.post.dto.request;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Sticker;
import jakarta.validation.constraints.NotNull;

public record StickerAddRequest(@NotNull String category,
                                @NotNull Double xCoordinate,
                                @NotNull Double yCoordinate,
                                @NotNull Long memberId) {

    public Sticker toStickerEntity(Member member) {
        return new Sticker(category, xCoordinate, yCoordinate, member);
    }
}
