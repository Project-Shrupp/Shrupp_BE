package com.shrupp.shrupp.domain.sticker.repository;

import com.shrupp.shrupp.domain.sticker.domain.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    @Query("select s from Sticker s left join fetch s.member")
    List<Sticker> findAllFetchWithMember();
}
