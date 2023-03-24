package com.shrupp.shrupp.domain.post.dto.response;

public record PostReportResponse(String reportType,
                                 Long postId,
                                 Long memberId) {
}
