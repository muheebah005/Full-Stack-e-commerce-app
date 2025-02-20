package com.mcoder.kclothing.cart.service.user;

import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mcoder.kclothing.cart.dto.UserDto;
import com.mcoder.kclothing.cart.model.Role;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.repository.RoleRepository;
import com.mcoder.kclothing.cart.repository.UserRepository;
import com.mcoder.kclothing.cart.request.CreateUserRequest;
import com.mcoder.kclothing.cart.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserById(Long userId) {
      return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found!"));
    }


    @Override
    public User createUser(CreateUserRequest request) {
       return Optional.of(request)
            .filter(user -> !userRepository.existsByEmail(request.getEmail()))
            .map(req -> {
                User user = new User();
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUserName(request.getUserName());
                Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Unable to set user role"));
                user.setRoles(Set.of(userRole));
                return userRepository.save(user);
            }).orElseThrow(() -> new RuntimeException("User with the email: " + request.getEmail() + " already Exist!"));
    }


    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
      return userRepository.findById(userId).map(existingUser -> {
        existingUser.setUserName(request.getUserName());
        return userRepository.save(existingUser);
      }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
       userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
        throw new RuntimeException("User not found!");
       });
    }

    @Override
    public UserDto convertUserToDto(User user){
      return modelMapper.map(user, UserDto.class);
    }


    @Override
    public User getAuthenticatedUser() {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String email = auth.getName();
      return userRepository.findByEmail(email);
    }
}
