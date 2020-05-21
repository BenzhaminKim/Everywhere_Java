package com.everywhere.dev.service;

import com.everywhere.dev.Repository.UserRepository;
import com.everywhere.dev.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        EverywhereUsers user = userRepository.findByUsername(userName);

        if(user == null){
            throw new UsernameNotFoundException(userName);
        }

        return new CustomUserDetails(user);
    }

    public UUID findUserIdByUsername(String username){
        return userRepository.findUserIdByUsername(username);
    }

    public EverywhereUsers save(EverywhereUsers user){
        return userRepository.save(user);
    }
}
