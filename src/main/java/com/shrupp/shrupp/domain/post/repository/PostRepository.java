package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByDeletedFalseAndIdAndMemberId(Long id, Long memberId);
    @Query("select p from Post p join fetch p.member where p.deleted = false and p.id = :id")
    Optional<Post> findByIdWithFetch(@Param("id") Long id);

    @Query("select p from Post p join fetch p.member " +
            "where p.deleted = false and p.member.deleted = false and p.id = :id and p.member.id = :memberId")
    Optional<Post> findByIdWithFetchMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    @Query(value = "select p from Post p join fetch p.member where p.deleted = false",
            countQuery = "select count(p) from Post p where p.deleted = false")
    Page<Post> findPagingAll(Pageable pageable);
}
