package com.capstone.userservice.security.services;

import com.capstone.userservice.models.User;
import com.capstone.userservice.repositories.UserRepository;
import com.capstone.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty())
        {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOptional.get();
        return new CustomUserDetails(user);
    }
}