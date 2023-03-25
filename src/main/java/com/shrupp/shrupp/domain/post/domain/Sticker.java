package com.shrupp.shrupp.domain.post.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.dto.response.StickerResponse;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String category;
    private Double xCoordinate;
    private Double yCoordinate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Sticker(String category, Double xCoordinate, Double yCoordinate, Member member) {
        this.category = category;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.member = member;
    }

    public StickerResponse toStickerResponse() {
        return new StickerResponse(category, xCoordinate, yCoordinate, member.getId());
    }
}
