package com.shrupp.shrupp.domain.sticker.dto.response;

import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import lombok.Builder;

@Builder
public record StickerResponse(Long id,
                              String category,
                              Double xCoordinate,
                              Double yCoordinate) {

    public static StickerResponse of(Sticker sticker) {
        return StickerResponse.builder()
                .id(sticker.getId())
                .category(sticker.getCategory())
                .xCoordinate(sticker.getXCoordinate())
                .yCoordinate(sticker.getYCoordinate())
                .build();
    }
}
