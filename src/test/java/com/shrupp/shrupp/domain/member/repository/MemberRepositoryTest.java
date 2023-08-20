package com.shrupp.shrupp.domain.member.repository;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("oauth account로 멤버를 조회할 수 있다")
    void findByAccount() {
        Oauth2 oauth2 = new Oauth2(AuthProvider.KAKAO, "account");
        Member member = new Member("nickname", oauth2);
        memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByOauth2Account(oauth2.getAccount());

        assertThat(findMember).isNotEmpty();
        assertThat(findMember).get().isEqualTo(member);
    }
}