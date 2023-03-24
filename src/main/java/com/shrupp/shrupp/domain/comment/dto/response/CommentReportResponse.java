package com.shrupp.shrupp.domain.comment.dto.response;

public record CommentReportResponse(String contentType,
                                    Long commentId,
                                    Long memberId) {
}
