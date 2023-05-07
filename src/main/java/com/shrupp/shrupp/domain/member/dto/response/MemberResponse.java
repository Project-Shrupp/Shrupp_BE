package com.shrupp.shrupp.domain.member.dto.response;

import com.shrupp.shrupp.domain.member.domain.Member;

import java.time.LocalDateTime;

public record MemberResponse(String nickname,
                             LocalDateTime created) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getNickname(), member.getCreated());
    }
}
