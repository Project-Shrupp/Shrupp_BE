package com.shrupp.shrupp.domain.member.repository;

import com.shrupp.shrupp.domain.member.entity.Nickname;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NicknameRepositoryTest {

    @Autowired NicknameRepository nicknameRepository;

    @Test
    @DisplayName("형용사를 랜덤으로 하나 가져올 수 있다")
    void findRandomAdjective() {
        String adjective = "행복한";
        nicknameRepository.save(new Nickname(adjective, "생활"));

        String randomAdjective = nicknameRepository.findRandomAdjective();

        assertThat(randomAdjective).isEqualTo(adjective);
    }

    @Test
    @DisplayName("명사를 랜덤으로 하나 가져올 수 있다")
    void findRandomNoun() {
        String noun = "생활";
        nicknameRepository.save(new Nickname("행복한", noun));

        String randomNoun = nicknameRepository.findRandomNoun();

        assertThat(randomNoun).isEqualTo(noun);
    }
}