package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.Genre;

@Repository
public interface GenreRepository extends BaseRepository<Genre, Long> {

}
