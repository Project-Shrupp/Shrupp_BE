package com.shrupp.shrupp.domain.member.repository;

import com.shrupp.shrupp.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauth2Account(String account);
}
