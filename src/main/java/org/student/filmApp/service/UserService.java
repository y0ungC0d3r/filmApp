package org.student.filmApp.service;

import org.student.filmApp.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);

    Optional<User> findByUsername(String username);
}
