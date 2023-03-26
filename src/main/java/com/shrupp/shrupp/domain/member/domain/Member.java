package com.shrupp.shrupp.domain.member.domain;

import com.shrupp.shrupp.domain.member.dto.response.MemberResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nickname;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> role = new ArrayList<>(List.of(Role.ROLE_USER));
    @Embedded
    private Oauth2 oauth2;

    public Member(String nickname, LocalDateTime created, LocalDateTime lastUpdated, Oauth2 oauth2) {
        this.nickname = nickname;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.oauth2 = oauth2;
    }

    public List<SimpleGrantedAuthority> getRole() {
        return role.stream()
                .map(Role::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(nickname, created);
    }
}
