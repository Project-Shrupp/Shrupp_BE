package com.shrupp.shrupp.domain.member.controller;

import com.shrupp.shrupp.domain.member.dto.response.MemberResponse;
import com.shrupp.shrupp.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> memberList() {
        return ResponseEntity.ok(memberService.findAll().stream()
                .map(MemberResponse::of)
                .toList());
    }
}
