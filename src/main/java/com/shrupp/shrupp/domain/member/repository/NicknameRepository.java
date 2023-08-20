package com.shrupp.shrupp.domain.member.repository;

import com.shrupp.shrupp.domain.member.entity.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {

    @Query("select n.adjective from Nickname n order by rand() limit 1")
    String findRandomAdjective();

    @Query("select n.noun from Nickname n order by rand() limit 1")
    String findRandomNoun();
}
