package com.shrupp.shrupp.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String noun;

    private String adjective;

    public Nickname(String noun, String adjective) {
        this.noun = noun;
        this.adjective = adjective;
    }
}
