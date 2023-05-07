package com.shrupp.shrupp.domain.post.domain;

import com.shrupp.shrupp.domain.member.domain.Member;
import com.shrupp.shrupp.global.audit.BaseTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private String backgroundColor;

//    @Embedded
//    private BaseTime baseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String content,
                String backgroundColor,
                Member member) {
        this.content = content;
        this.backgroundColor = backgroundColor;
//        this.baseTime = new BaseTime();
        this.member = member;
    }

    public void updatePost(String content, String backgroundColor) {
        this.content = content;
        this.backgroundColor = backgroundColor;
    }
}
