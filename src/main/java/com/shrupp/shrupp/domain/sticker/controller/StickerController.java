package com.shrupp.shrupp.domain.sticker.controller;

import com.shrupp.shrupp.config.security.LoginUser;
import com.shrupp.shrupp.domain.sticker.dto.request.StickerAddRequest;
import com.shrupp.shrupp.domain.sticker.dto.response.StickerResponse;
import com.shrupp.shrupp.domain.sticker.service.StickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/stickers")
@RestController
public class StickerController {

    private final StickerService stickerService;

    @GetMapping
    public ResponseEntity<List<StickerResponse>> stickerList(@PathVariable Long postId) {
        return ResponseEntity.ok(stickerService.findByPostId(postId).stream()
                .map(StickerResponse::of)
                .toList());
    }

    @PostMapping
    public ResponseEntity<StickerResponse> stickerAdd(@RequestBody @Validated StickerAddRequest stickerAddRequest,
                                                      @PathVariable Long postId,
                                                      @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(StickerResponse.of(stickerService.save(stickerAddRequest, loginUser.getMember().getId(), postId)));
    }

    @DeleteMapping("/{stickerId}")
    public ResponseEntity<Objects> stickerDelete(@PathVariable Long stickerId) {
        stickerService.deleteById(stickerId);

        return ResponseEntity.ok().build();
    }
}
