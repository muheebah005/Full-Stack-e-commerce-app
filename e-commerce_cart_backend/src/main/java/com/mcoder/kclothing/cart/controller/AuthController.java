package com.mcoder.kclothing.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.request.LoginRequest;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.response.JwtResponse;
import com.mcoder.kclothing.security.jwt.JwtUtils;
import com.mcoder.kclothing.security.user.AppUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
       try {
         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = jwtUtils.generateTokenForUser(authentication);
         AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
         JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
         return ResponseEntity.ok(new ApiResponse(true, "Login Successful!", jwtResponse));
     } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage(), null));
       }
}
}