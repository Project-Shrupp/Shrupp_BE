package com.shrupp.shrupp.domain.sticker.dto.response;

import com.shrupp.shrupp.domain.sticker.domain.Sticker;
import lombok.Builder;

@Builder
public record StickerResponse(String category,
                              Double xCoordinate,
                              Double yCoordinate,
                              Long memberId) {

    public static StickerResponse of(Sticker sticker) {
        return StickerResponse.builder()
                .category(sticker.getCategory())
                .xCoordinate(sticker.getXCoordinate())
                .yCoordinate(sticker.getYCoordinate())
                .memberId(sticker.getMember().getId())
                .build();
    }
}
