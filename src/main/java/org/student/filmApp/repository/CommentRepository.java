package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.Comment;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> { }
