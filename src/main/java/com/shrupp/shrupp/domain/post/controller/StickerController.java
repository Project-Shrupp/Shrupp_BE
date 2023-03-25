package com.shrupp.shrupp.domain.post.controller;

import com.shrupp.shrupp.domain.post.domain.Sticker;
import com.shrupp.shrupp.domain.post.dto.request.StickerAddRequest;
import com.shrupp.shrupp.domain.post.dto.response.StickerResponse;
import com.shrupp.shrupp.domain.post.service.StickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/v1/stickers")
@RestController
public class StickerController {

    private final StickerService stickerService;

    @GetMapping
    public ResponseEntity<List<StickerResponse>> stickerList() {
        return ResponseEntity.ok(stickerService.findAll().stream()
                .map(Sticker::toStickerResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<StickerResponse> stickerAdd(@RequestBody @Validated StickerAddRequest stickerAddRequest) {
        return ResponseEntity.ok(stickerService.save(stickerAddRequest).toStickerResponse());
    }

    @DeleteMapping("/{stickerId}")
    public ResponseEntity<Objects> stickerDelete(@PathVariable Long stickerId) {
        stickerService.deleteById(stickerId);

        return ResponseEntity.ok().build();
    }
}
