package com.shrupp.shrupp.domain.comment.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private BaseTime baseTime;

    private String reportType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CommentReport(String reportType, Comment comment, Member member) {
        this.reportType = reportType;
        this.baseTime = new BaseTime();
        this.comment = comment;
        this.member = member;
    }
}
