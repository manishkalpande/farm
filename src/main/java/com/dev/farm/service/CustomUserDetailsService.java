package com.dev.farm.service;

import com.dev.farm.config.SecurityConfig;
import com.dev.farm.entity.User;
import com.dev.farm.entity.UserPrincipal;
import com.dev.farm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);


    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(securityConfig.passwordEncoder().encode(user.getPassword()))
//                .authorities("USER")
//                .build();
//        return userDetails;
        return new UserPrincipal(user);
    }
}
