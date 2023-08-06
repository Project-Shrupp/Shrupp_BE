package com.shrupp.shrupp.domain.member.service;

import com.shrupp.shrupp.domain.member.repository.NicknameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberNicknameGenerateService {

    private final NicknameRepository nicknameRepository;

    public String generateMemberNickname() {
        return nicknameRepository.findRandomNoun() + " " + nicknameRepository.findRandomAdjective();
    }
}
