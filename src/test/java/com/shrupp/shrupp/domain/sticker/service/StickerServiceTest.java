package com.shrupp.shrupp.domain.sticker.service;

import com.shrupp.shrupp.config.security.oauth2.AuthProvider;
import com.shrupp.shrupp.domain.member.entity.Member;
import com.shrupp.shrupp.domain.member.entity.Oauth2;
import com.shrupp.shrupp.domain.member.service.MemberService;
import com.shrupp.shrupp.domain.post.entity.Post;
import com.shrupp.shrupp.domain.post.service.PostService;
import com.shrupp.shrupp.domain.sticker.dto.request.StickerAddRequest;
import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import com.shrupp.shrupp.domain.sticker.repository.StickerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StickerServiceTest {

    @Mock PostService postService;
    @Mock MemberService memberService;
    @Mock StickerRepository stickerRepository;
    @InjectMocks StickerService stickerService;

    @Test
    @DisplayName("스티커를 저장할 수 있다")
    void saveSticker() {
        long memberId = 1L;
        long postId = 1L;
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        Post post = new Post("content", "#ffffff", member);
        Sticker sticker = new Sticker("category", 1.1, 1.2, member, post);
        StickerAddRequest request = new StickerAddRequest("category", 1.1, 1.2);
        given(stickerRepository.save(any(Sticker.class))).willReturn(sticker);

        Sticker savedSticker = stickerService.save(request, memberId, postId);

        assertThat(savedSticker).isEqualTo(sticker);
    }

    @Test
    @DisplayName("전체 스티커를 조회할 수 있다")
    void findAllSticker() {
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        Post post = new Post("content", "#ffffff", member);
        Sticker sticker = new Sticker("category", 1.1, 1.2, member, post);
        given(stickerRepository.findAllFetchWithMember()).willReturn(List.of(sticker));

        List<Sticker> findStickers = stickerService.findAll();

        assertThat(findStickers).hasSize(1);
        assertThat(findStickers).containsExactly(sticker);
    }

    @Test
    @DisplayName("특정 게시글의 스티커를 조회할 수 있다")
    void findStickerByPostId() {
        long postId = 1L;
        Member member = new Member("nickname", new Oauth2(AuthProvider.KAKAO, "account"));
        Post post = new Post("content", "#ffffff", member);
        Sticker sticker = new Sticker("category", 1.1, 1.2, member, post);
        given(stickerRepository.findByPostIdFetchWithMember(any(Long.class))).willReturn(List.of(sticker));

        List<Sticker> findStickers = stickerService.findByPostId(postId);

        assertThat(findStickers).hasSize(1);
        assertThat(findStickers).containsExactly(sticker);
    }

    @Test
    @DisplayName("스티커 id로 스티커를 삭제할 수 있다")
    void deleteStickerByStickerId() {
        long stickerId = 1L;
        doNothing().when(stickerRepository).deleteById(any(Long.class));

        stickerService.deleteById(stickerId);

        verify(stickerRepository, times(1)).deleteById(stickerId);
    }
}