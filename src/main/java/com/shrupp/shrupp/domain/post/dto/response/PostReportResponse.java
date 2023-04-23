package com.shrupp.shrupp.domain.post.dto.response;

import com.shrupp.shrupp.domain.post.domain.PostReport;

public record PostReportResponse(String reportType,
                                 Long postId,
                                 Long memberId) {

    public static PostReportResponse of(PostReport postReport) {
        return new PostReportResponse(postReport.getReportType(), postReport.getPost().getId(), postReport.getMember().getId());
    }
}
