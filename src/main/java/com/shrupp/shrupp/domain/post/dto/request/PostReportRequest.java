package com.shrupp.shrupp.domain.post.dto.request;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.domain.PostReport;
import jakarta.validation.constraints.NotNull;

public record PostReportRequest(@NotNull String reportType) {

    public PostReport toPostReport(Post post, Member member) {
        return new PostReport(reportType, post, member);
    }
}
