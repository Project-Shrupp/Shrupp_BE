package com.shrupp.shrupp.domain.sticker.entity;

import com.shrupp.shrupp.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double xCoordinate;

    @Column(nullable = false)
    private Double yCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Sticker(String category, Double xCoordinate, Double yCoordinate, Member member) {
        this.category = category;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.member = member;
    }
}
