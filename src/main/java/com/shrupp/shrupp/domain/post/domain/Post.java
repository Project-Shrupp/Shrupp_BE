package com.shrupp.shrupp.domain.post.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.dto.response.PostResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private String background;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String content,
                String background,
                LocalDateTime created,
                LocalDateTime lastUpdated,
                Member member) {
        this.content = content;
        this.background = background;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.member = member;
    }

    public void updatePost(String content, String background) {
        this.content = content;
        this.background = background;
        this.lastUpdated = LocalDateTime.now();
    }

    public PostResponse toPostResponse() {
        return new PostResponse(content, background, created, lastUpdated, member.getNickname());
    }
}
