package com.shrupp.shrupp.domain.post.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.dto.response.PostReportResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String reportType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public PostReport(String reportType, Post post, Member member) {
        this.reportType = reportType;
        this.post = post;
        this.member = member;
    }

    public PostReportResponse toPostReportResponse() {
        return new PostReportResponse(reportType, post.getId(), member.getId());
    }
}
