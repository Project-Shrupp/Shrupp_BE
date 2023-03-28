package com.shrupp.shrupp.domain.post.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.domain.post.dto.response.PostResponse;
import com.shrupp.shrupp.domain.post.dto.response.SimplePostResponse;
import com.shrupp.shrupp.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private String backgroundColor;

    @Embedded
    private BaseTime baseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String content,
                String backgroundColor,
                Member member) {
        this.content = content;
        this.backgroundColor = backgroundColor;
        this.baseTime = new BaseTime();
        this.member = member;
    }

    public void updatePost(String content, String backgroundColor) {
        this.content = content;
        this.backgroundColor = backgroundColor;
    }

    public PostResponse toPostResponse() {
        return new PostResponse(content, backgroundColor, baseTime.getCreated(), baseTime.getLastUpdated(), member.getNickname());
    }

    public SimplePostResponse toSimplePostResponse() {
        return new SimplePostResponse(id, content, backgroundColor, baseTime.getCreated());
    }
}
