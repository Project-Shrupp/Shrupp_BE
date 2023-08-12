package com.shrupp.shrupp.domain.comment.entity;

import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CommentReport extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String reportType;

    @Column(nullable = false)
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CommentReport(String reportType, Comment comment, Member member) {
        this.reportType = reportType;
        this.comment = comment;
        this.member = member;
        this.deleted = false;
    }
}
