package com.shrupp.shrupp.domain.member.dto.response;

import java.time.LocalDateTime;

public record MemberResponse(String nickname,
                             LocalDateTime created) {
}
