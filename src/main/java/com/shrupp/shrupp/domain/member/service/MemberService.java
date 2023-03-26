package com.shrupp.shrupp.domain.member.service;

import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.domain.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveIfNotExists(OAuth2Request oAuth2Request) {
        return findByOAuth2Id(oAuth2Request.getAccountId())
                .orElseGet(() -> save(oAuth2Request));
    }

    public Member save(OAuth2Request oAuth2Request) {
        return memberRepository.save(new Member(oAuth2Request.getName(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new Oauth2(oAuth2Request.getAuthProvider(), oAuth2Request.getAccountId())));
    }

    public Optional<Member> findByOAuth2Id(String account) {
        return memberRepository.findByOauth2Account(account);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
