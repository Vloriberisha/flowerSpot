package com.povio.exam.flowerspot.controller;

import com.povio.exam.flowerspot.dto.AuthenticationRequestDTO;
import com.povio.exam.flowerspot.dto.AuthenticationResponseDTO;
import com.povio.exam.flowerspot.model.User;
import com.povio.exam.flowerspot.repository.UserRepository;
import com.povio.exam.flowerspot.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                    UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        log.info("hello");
        return ResponseEntity.ok("hello");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Bad credentials..");
            throw new Exception("Username or password are incorrect", e);
        }
        final String jwt = jwtUtil.generateJwtToken(request.getUsername());
        log.info("User " + request.getUsername() + " successfully logged in.");
        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByUsernameOrMail(user.getUsername(), user.getMail())) {
            return ResponseEntity.badRequest().body("Username/mail are already used.");
        }
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User " + user.getUsername() + " successfully registered.");

        return ResponseEntity.ok("Registration completed.");
    }
}
