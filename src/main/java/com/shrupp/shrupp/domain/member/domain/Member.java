package com.shrupp.shrupp.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nickname;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public Member(String nickname, LocalDateTime created, LocalDateTime lastUpdated) {
        this.nickname = nickname;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }
}
