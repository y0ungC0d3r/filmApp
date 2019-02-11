package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.filmApp.domain.Role;
import org.student.filmApp.domain.User;
import org.student.filmApp.repository.RoleRepository;
import org.student.filmApp.repository.UserRepository;

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
    @Autowired
    private SecurityService securityService;

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

    public User findLoggedUser() throws IllegalAccessException {
        Optional<User> loggedUser = findByUsername(securityService.findLoggedInUsername());
        return loggedUser.orElseThrow(IllegalAccessException::new);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}