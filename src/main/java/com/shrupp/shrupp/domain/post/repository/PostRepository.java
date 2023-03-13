package com.shrupp.shrupp.domain.post.repository;

import com.shrupp.shrupp.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
