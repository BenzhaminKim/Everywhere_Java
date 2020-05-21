package com.everywhere.dev.controller;

import com.everywhere.dev.Config.JwtUtil;
import com.everywhere.dev.model.*;
import com.everywhere.dev.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password" , e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public EverywhereUsers createUser(@Valid @RequestBody EverywhereUsers user){
        return userDetailsService.save(user);
    }

    @PostMapping("/username")
    public String getUsername(@Valid @RequestBody Token token){
        return jwtUtil.extractUsername(token.getToken());
    }

    @PostMapping("/userid")
    public UUID getUserId(@Valid @RequestBody Token token){
        String username = jwtUtil.extractUsername(token.getToken());
        return userDetailsService.findUserIdByUsername(username);
    }
    @PostMapping("/validate")
    public Boolean isTokenValidate(@Valid @RequestBody Token token){
        if(token.getToken() == null){
            return Boolean.FALSE;
        }
        final String username = jwtUtil.extractUsername(token.getToken());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return jwtUtil.validateToken(token.getToken(), userDetails);
    }

}
