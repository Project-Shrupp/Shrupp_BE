package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.post.domain.PostReport;

import java.time.LocalDateTime;

public record PostReportResponse(String reportType,
                                 LocalDateTime created,
                                 Long postId,
                                 Long memberId) {

    public static PostReportResponse of(PostReport postReport) {
        return new PostReportResponse(postReport.getReportType(), postReport.getBaseTime().getCreated(),
                postReport.getPost().getId(), postReport.getMember().getId());
    }
}
