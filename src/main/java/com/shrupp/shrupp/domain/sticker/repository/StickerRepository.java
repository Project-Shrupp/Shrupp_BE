package com.shrupp.shrupp.domain.sticker.repository;

import com.shrupp.shrupp.domain.sticker.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    @Query("select s from Sticker s left join fetch s.member where s.member.deleted = false")
    List<Sticker> findAllFetchWithMember();

    @Query("select s from Sticker s left join fetch s.member " +
            "where s.member.deleted = false and s.post.deleted = false and s.post.id = :postId")
    List<Sticker> findByPostIdFetchWithMember(@Param("postId") Long postId);
}
