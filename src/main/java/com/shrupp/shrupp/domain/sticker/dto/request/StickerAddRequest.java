package com.shrupp.shrupp.domain.sticker.dto.request;

import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import jakarta.validation.constraints.NotNull;

public record StickerAddRequest(@NotNull String category,
                                @NotNull Double xCoordinate,
                                @NotNull Double yCoordinate) {

    public Sticker toStickerEntity(Member member) {
        return new Sticker(category, xCoordinate, yCoordinate, member);
    }
}
