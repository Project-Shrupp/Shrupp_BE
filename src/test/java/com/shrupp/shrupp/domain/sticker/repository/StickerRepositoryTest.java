package com.shrupp.shrupp.domain.sticker.repository;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.repository.MemberRepository;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StickerRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired StickerRepository stickerRepository;

    @Test
    @DisplayName("스티커 전체 목록을 조회할 수 있다")
    void findAllFetchWithMember() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Sticker sticker1 = new Sticker("category1", 1.1, 1.2, member, post);
        Sticker sticker2 = new Sticker("category2", 1.1, 1.2, member, post);
        stickerRepository.save(sticker1);
        stickerRepository.save(sticker2);

        List<Sticker> findStickers = stickerRepository.findAllFetchWithMember();

        assertThat(findStickers).hasSize(2);
        assertThat(findStickers).containsExactly(sticker1, sticker2);
    }

    @Test
    @DisplayName("게시글 id로 스티커 목록을 조회할 수 있다")
    void findByPostIdFetchWithMember() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        memberRepository.save(member);
        Post post = new Post("content", "#ffffff", member);
        postRepository.save(post);
        Sticker sticker1 = new Sticker("category1", 1.1, 1.2, member, post);
        Sticker sticker2 = new Sticker("category2", 1.1, 1.2, member, post);
        stickerRepository.save(sticker1);
        stickerRepository.save(sticker2);

        List<Sticker> findStickers = stickerRepository.findByPostIdFetchWithMember(post.getId());

        assertThat(findStickers).hasSize(2);
        assertThat(findStickers).containsExactly(sticker1, sticker2);
    }
}