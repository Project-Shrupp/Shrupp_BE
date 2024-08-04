package com.shrupp.shrupp.domain.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.entity.PostLike;
import com.shrupp.shrupp.domain.post.service.PostLikeService;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostLikeRepositoryTest {

    @Autowired
    PostLikeService postLikeService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    Member member;
    Post post;

    @BeforeEach
    void init() {
        member = memberRepository.save(
                new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account")));
        post = postRepository.save(new Post("content", "#fff", member));
    }

    @Test
    @DisplayName("post 좋아요를 중복해서 입력할 수 없다.")
    void postLikeCanNotBeDuplicated() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    postLikeService.like(post.getId(), member.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        assertThat(postLikeService.getPostLikeCount(post.getId())).isEqualTo(1);
    }
}