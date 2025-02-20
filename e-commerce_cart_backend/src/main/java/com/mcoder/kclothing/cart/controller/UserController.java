package com.mcoder.kclothing.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.dto.UserDto;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.request.CreateUserRequest;
import com.mcoder.kclothing.cart.request.UserUpdateRequest;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.service.user.UserServiceInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
       try {
         User user = userService.getUserById(userId);
         UserDto userDto = userService.convertUserToDto(user);
         return ResponseEntity.ok(new ApiResponse(true, "Success", userDto));
       } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found!", null));
       }
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
      try {
          User user = userService.createUser(request);
          UserDto userDto = userService.convertUserToDto(user);
          return ResponseEntity.ok(new ApiResponse(true, "User Registered Successfully", userDto));
      } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "An Error Occured while trying to register!"));
      }
    }


    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found", null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse(true, "User Deleted!", null));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found!", null));
        }
    }
}
