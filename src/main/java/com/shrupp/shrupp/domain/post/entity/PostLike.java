package com.shrupp.shrupp.domain.post.entity;

import com.shrupp.shrupp.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostLike {

    @EmbeddedId
    private PostLikeId id;


    public PostLike(Post post, Member member) {
        this.id = new PostLikeId(post, member);
    }
}
