package com.shrupp.shrupp.domain.comment.domain;

import com.shrupp.shrupp.domain.comment.dto.response.CommentResponse;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(String content,
                   LocalDateTime created,
                   LocalDateTime lastUpdated,
                   Post post,
                   Member member) {
        this.content = content;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.post = post;
        this.member = member;
    }

    public CommentResponse toCommentResponse() {
        return new CommentResponse(id, content, created, lastUpdated, member.getNickname());
    }

    public void updateComment(String content) {
        this.content = content;
        this.lastUpdated = LocalDateTime.now();
    }
}
