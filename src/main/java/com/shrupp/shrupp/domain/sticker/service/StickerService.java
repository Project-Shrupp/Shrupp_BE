package com.shrupp.shrupp.domain.sticker.service;

import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import com.shrupp.shrupp.domain.sticker.dto.request.StickerAddRequest;
import com.shrupp.shrupp.domain.sticker.repository.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StickerService {

    private final StickerRepository stickerRepository;
    private final MemberService memberService;

    @Transactional
    public Sticker save(StickerAddRequest stickerAddRequest, Long memberId) {
        return stickerRepository.save(stickerAddRequest.toStickerEntity(
                memberService.findById(memberId)));
    }

    public List<Sticker> findAll() {
        return stickerRepository.findAllFetchWithMember();
    }

    @Transactional
    public void deleteById(Long stickerId) {
        stickerRepository.deleteById(stickerId);
    }
}
