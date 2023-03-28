package com.shrupp.shrupp.domain.comment.domain;

import com.shrupp.shrupp.domain.comment.dto.response.CommentResponse;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;

    @Embedded
    private BaseTime baseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(String content,
                   Post post,
                   Member member) {
        this.content = content;
        this.baseTime = new BaseTime();
        this.post = post;
        this.member = member;
    }

    public CommentResponse toCommentResponse() {
        return new CommentResponse(id, content, baseTime.getCreated(), baseTime.getLastUpdated(), member.getNickname());
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
