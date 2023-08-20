package com.shrupp.shrupp.domain.member.service;

import com.shrupp.shrupp.domain.member.repository.NicknameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberNicknameGenerateServiceTest {

    @Mock NicknameRepository nicknameRepository;
    @InjectMocks MemberNicknameGenerateService nicknameGenerateService;

    @Test
    @DisplayName("닉네임을 생성할 수 있다")
    void generateMemberNickname() {
        String adjective = "행복한";
        String noun = "웃음";
        String nickname = adjective + " " + noun;
        given(nicknameRepository.findRandomAdjective()).willReturn(adjective);
        given(nicknameRepository.findRandomNoun()).willReturn(noun);

        String generateNickname = nicknameGenerateService.generateMemberNickname();

        assertThat(generateNickname).isEqualTo(nickname);
    }
}