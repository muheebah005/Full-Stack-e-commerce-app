package com.mcoder.kclothing.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mcoder.kclothing.cart.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDetails implements UserDetails{

    private Long id;
    private String email;
    private String password;

    private Collection<GrantedAuthority> authorities;

    public static AppUserDetails buildUserDetails(User user){
        List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
                return new AppUserDetails(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
    }

    @Override
    public String getPassword() {
     return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    
}
