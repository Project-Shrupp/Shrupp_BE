package com.shrupp.shrupp.domain.comment.dto.request;

import com.shrupp.shrupp.domain.comment.domain.Comment;
import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.domain.Post;
import jakarta.validation.constraints.NotNull;

public record CommentRegisterRequest(@NotNull String content,
                                     @NotNull Long postId) {

    public Comment toCommentEntity(Post post, Member member) {
        return Comment.builder()
                .content(content)
                .post(post)
                .member(member)
                .build();
    }
}
