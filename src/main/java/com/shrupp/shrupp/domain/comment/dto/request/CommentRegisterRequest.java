package com.shrupp.shrupp.domain.comment.dto.request;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentRegisterRequest(@NotNull String content,
                                     @NotNull Long memberId,
                                     @NotNull Long postId) {

    public Comment toCommentEntity(Post post, Member member) {
        return Comment.builder()
                .content(content)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .post(post)
                .member(member)
                .build();
    }
}
