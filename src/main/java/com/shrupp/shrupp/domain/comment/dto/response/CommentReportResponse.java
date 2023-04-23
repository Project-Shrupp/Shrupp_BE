package com.shrupp.shrupp.domain.comment.dto.response;

import com.shrupp.shrupp.domain.comment.domain.CommentReport;

public record CommentReportResponse(String reportType,
                                    Long commentId,
                                    Long memberId) {

    public static CommentReportResponse of(CommentReport commentReport) {
        return new CommentReportResponse(commentReport.getReportType(), commentReport.getComment().getId(), commentReport.getMember().getId());
    }
}
