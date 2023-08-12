package com.shrupp.shrupp.domain.comment.entity;

import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 5000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

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
        this.post = post;
        this.member = member;
        this.deleted = false;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }
}
