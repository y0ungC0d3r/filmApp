package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
