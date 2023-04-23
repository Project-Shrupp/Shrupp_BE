package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member where p.id = :id")
    Optional<Post> findByIdWithFetch(@Param("id") Long id);
}
