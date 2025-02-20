package com.mcoder.kclothing.security.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = Optional.ofNullable(userRepository.findByEmail(email))
                            .orElseThrow(() -> new RuntimeException("User does not exist!"));
      return AppUserDetails.buildUserDetails(user);
    }

}
