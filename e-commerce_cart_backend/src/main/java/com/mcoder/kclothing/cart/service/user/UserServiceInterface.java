package com.mcoder.kclothing.cart.service.user;

import com.mcoder.kclothing.cart.dto.UserDto;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.request.CreateUserRequest;
import com.mcoder.kclothing.cart.request.UserUpdateRequest;

public interface UserServiceInterface {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    User getAuthenticatedUser();
}
