package com.shrupp.shrupp.domain.member.domain;

import com.shrupp.shrupp.domain.member.dto.response.MemberResponse;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nickname;

    @Embedded
    private BaseTime baseTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> role = new ArrayList<>(List.of(Role.ROLE_USER));
    @Embedded
    private Oauth2 oauth2;

    public Member(String nickname, LocalDateTime created, LocalDateTime lastUpdated, Oauth2 oauth2) {
        this.nickname = nickname;
        this.baseTime = new BaseTime();
        this.oauth2 = oauth2;
    }

    public List<SimpleGrantedAuthority> getRole() {
        return role.stream()
                .map(Role::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(nickname, baseTime.getCreated());
    }
}
