package com.shrupp.shrupp.domain.post.service;

import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.domain.PostReport;
import com.shrupp.shrupp.domain.post.dto.request.PostReportRequest;
import com.shrupp.shrupp.domain.post.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostService postService;
    private final MemberService memberService;

    public PostReport report(Long postId, PostReportRequest postReportRequest, Long memberId) {
        return postReportRepository.save(postReportRequest.toPostReport(
                        postService.findById(postId),
                        memberService.findById(memberId)));
    }
}
