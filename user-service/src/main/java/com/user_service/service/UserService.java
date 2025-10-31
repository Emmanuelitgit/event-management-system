package com.user_service.service;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
    ResponseDTO createUser(User user);
    ResponseDTO updateUser(User user);
    ResponseDTO findAllUsers(int page, int size, String search);
    ResponseDTO findUserById(UUID id);
    ResponseDTO deleteUserById(UUID id);
    ResponseDTO updateUserAddress(UUID id, String address);
}
