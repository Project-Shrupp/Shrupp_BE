package com.shrupp.shrupp.domain.comment.dto.request;

import com.shrupp.shrupp.domain.comment.entity.Comment;
import com.shrupp.shrupp.domain.comment.entity.CommentReport;
import com.shrupp.shrupp.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record CommentReportRequest(@NotNull String reportType) {

    public CommentReport toCommentReport(Comment comment, Member member) {
        return new CommentReport(reportType, comment, member);
    }
}
