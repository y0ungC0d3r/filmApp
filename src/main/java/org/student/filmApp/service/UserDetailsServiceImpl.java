package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.student.filmApp.entity.User;
import org.student.filmApp.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("aaaa  " + user.get().getPassword());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.ifPresent(u -> u.getRoles().forEach(r -> grantedAuthorities.add(new SimpleGrantedAuthority(r.getName()))));
        return new org.springframework.security.core.userdetails.User(user.map(u -> u.getUsername()).orElse(null),
                user.map(u -> u.getPassword()).orElse(null), grantedAuthorities);
    }
}
