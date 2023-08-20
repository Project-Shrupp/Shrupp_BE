package com.shrupp.shrupp.domain.member.service;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("멤버를 저장할 수 있다")
    void save() {
        OAuth2Request oAuth2Request =
                new OAuth2Request("account", "name", "test@mail.com", AuthProvider.KAKAO);
        Member member = new Member("name", new Oauth2(AuthProvider.KAKAO, "account"));
        given(memberRepository.save(any(Member.class))).willReturn(member);

        Member savedMember = memberService.save(oAuth2Request);

        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    @DisplayName("멤버가 존재하지 않는다면 저장할 수 있다")
    void saveIfNotExists() {
        OAuth2Request oAuth2Request =
                new OAuth2Request("account", "name", "test@mail.com", AuthProvider.KAKAO);
        Member member = new Member("name", new Oauth2(AuthProvider.KAKAO, "account"));
        given(memberRepository.findByOauth2Account(any(String.class))).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(member);

        Member savedMember = memberService.saveIfNotExists(oAuth2Request);

        assertThat(savedMember.getOauth2().getAccount()).isEqualTo(oAuth2Request.getAccountId());
    }

    @Test
    @DisplayName("멤버 id로 멤버를 조회할 수 있다")
    void findByMemberId() {
        Member member = new Member("name", new Oauth2(AuthProvider.KAKAO, "account"));
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(member));

        Member findMember = memberService.findById(1L);

        assertThat(findMember).isEqualTo(member);
    }
}