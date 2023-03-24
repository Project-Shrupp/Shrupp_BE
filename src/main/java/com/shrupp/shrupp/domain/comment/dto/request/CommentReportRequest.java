package com.shrupp.shrupp.domain.comment.dto.request;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.comment.domain.CommentReport;
import com.shrupp.shrupp.domain.member.domain.Member;
import jakarta.validation.constraints.NotNull;

public record CommentReportRequest(@NotNull String reportType,
                                   @NotNull Long memberId) {

    public CommentReport toCommentReport(Comment comment, Member member) {
        return new CommentReport(reportType, comment, member);
    }
}
