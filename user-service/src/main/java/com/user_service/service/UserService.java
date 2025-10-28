package com.user_service.service;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
    ResponseEntity<ResponseDTO> createUser(User user);
    ResponseEntity<ResponseDTO> updateUser(User user);
    ResponseEntity<ResponseDTO> findUserById(UUID id);
    ResponseEntity<ResponseDTO> deleteUserById(UUID id);
}
