package com.shrupp.shrupp.domain.comment.repository;

import com.shrupp.shrupp.domain.comment.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
