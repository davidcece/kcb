package com.cece.kcb.service;

import com.cece.kcb.entity.User;
import com.cece.kcb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(final String username, final String password) {
        String encodePassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodePassword);
        user.setRoles(Set.of("USER"));
        userRepository.save(user);
    }

    public boolean validateCredentials(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        return userDetails != null && passwordEncoder.matches(password, userDetails.getPassword());
    }
}