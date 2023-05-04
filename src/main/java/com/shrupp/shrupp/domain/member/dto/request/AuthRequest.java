package com.shrupp.shrupp.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(@NotNull String authToken) {
}
