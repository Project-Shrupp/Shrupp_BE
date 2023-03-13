package com.shrupp.shrupp.domain.member.repository;

import com.shrupp.shrupp.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
