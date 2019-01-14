package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.filmApp.entity.Role;
import org.student.filmApp.entity.User;
import org.student.filmApp.repository.RoleRepository;
import org.student.filmApp.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String USER_ROLE = "ROLE_USER";

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles =  roleRepository.findAll().stream()
                .filter(r -> r.getName().equals(USER_ROLE))
                .collect(Collectors.toSet());
        roles.forEach(r -> System.out.println(r.getName()));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}