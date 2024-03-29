package com.shrupp.shrupp.domain.comment.service;

import com.shrupp.shrupp.domain.comment.entity.CommentReport;
import com.shrupp.shrupp.domain.comment.dto.request.CommentReportRequest;
import com.shrupp.shrupp.domain.comment.repository.CommentReportRepository;
import com.shrupp.shrupp.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentService commentService;
    private final MemberService memberService;

    public CommentReport report(Long commentId, CommentReportRequest commentReportRequest, Long memberId) {
        return commentReportRepository.save(commentReportRequest.toCommentReport(
                commentService.findById(commentId),
                memberService.findById(memberId)));
    }
}
