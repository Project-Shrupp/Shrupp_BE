package com.shrupp.shrupp.domain.comment.domain;

import com.shrupp.shrupp.domain.comment.dto.response.CommentReportResponse;
import com.shrupp.shrupp.domain.member.domain.Member;
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

    private String reportType;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public CommentReport(String reportType, Comment comment, Member member) {
        this.reportType = reportType;
        this.comment = comment;
        this.member = member;
    }

    public CommentReportResponse toCommentReportResponse() {
        return new CommentReportResponse(reportType, comment.getId(), member.getId());
    }
}
