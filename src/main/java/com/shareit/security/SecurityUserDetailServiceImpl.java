package com.shareit.security;

import com.shareit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new SecurityUser(
                        user,
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getUserRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

}
