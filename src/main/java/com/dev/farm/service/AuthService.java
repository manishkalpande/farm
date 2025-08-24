package com.dev.farm.service;

import com.dev.farm.dto.AuthRequest;
import com.dev.farm.dto.AuthResponse;
import com.dev.farm.entity.User;
import com.dev.farm.entity.UserPrincipal;
import com.dev.farm.repository.UserRepository;
import com.dev.farm.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication=null;
        try {
           authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        final String jwt = jwtUtil.generateToken(userPrincipal.getUsername());

        return new AuthResponse(jwt);
    }

    public AuthRequest register(AuthRequest authRequest) {

        if(Objects.isNull(authRequest.getUsername()) || Objects.isNull(authRequest.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        Optional<User> user=userRepository.findByUsername(authRequest.getUsername());
        if(user.isPresent()) {
            throw new UsernameNotFoundException("Username already exists");
        }
        User userDetails=new User();
        userDetails.setUsername(authRequest.getUsername());
        userDetails.setPassword(authRequest.getPassword());
        userDetails.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userDetails=userRepository.save(userDetails);


        return new AuthRequest(userDetails.getUsername(),null);
    }
}
