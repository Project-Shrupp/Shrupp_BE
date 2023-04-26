package com.shrupp.shrupp.domain.member.service;

import com.shrupp.shrupp.config.security.oauth2.OAuth2Request;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.member.domain.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveIfNotExists(OAuth2Request oAuth2Request) {
        return memberRepository.findByOauth2Account(oAuth2Request.getAccountId())
                .orElseGet(() -> save(oAuth2Request));
    }

    @Transactional
    public Member save(OAuth2Request oAuth2Request) {
        return memberRepository.save(new Member(oAuth2Request.getName(), new Oauth2(oAuth2Request.getAuthProvider(), oAuth2Request.getAccountId())));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
