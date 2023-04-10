package com.shrupp.shrupp.domain.comment.dto.response;

public record CommentReportResponse(String reportType,
                                    Long commentId,
                                    Long memberId) {
}
