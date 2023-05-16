package com.shrupp.shrupp.domain.comment.dto.response;

import com.shrupp.shrupp.domain.comment.entity.CommentReport;

import java.time.LocalDateTime;

public record CommentReportResponse(String reportType,
                                    LocalDateTime created,
                                    Long commentId,
                                    Long memberId) {

    public static CommentReportResponse of(CommentReport commentReport) {
        return new CommentReportResponse(commentReport.getReportType(), commentReport.getCreated(),
                commentReport.getComment().getId(), commentReport.getMember().getId());
    }
}
