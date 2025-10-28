package com.user_service.serviceImpl;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import com.user_service.repo.UserRepo;
import com.user_service.service.UserService;
import com.user_service.util.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * @description This method is used to create a new user record
     * @param user the payload data of the user to be created
     * @return ResponseEntity containing the saved user record and status information
     * @auther Emmanuel Yidana
     * @createdAt 28th October 2025
     */
    @Override
    public ResponseEntity<ResponseDTO> createUser(User user) {
        try {
            ResponseDTO responseDTO;
            log.info("In create user method:->>{}", user);
            /**
             * check if user already exist by email
             */
            log.info("About to check if email already exist");
            Optional<User> emailExistOptional = userRepo.findUserByEmail(user.getEmail());
            if (emailExistOptional.isPresent()){
                log.error("Email already exist:->>{}", user.getEmail());
                responseDTO = AppUtils.getResponseDto("Email already exist", HttpStatus.ALREADY_REPORTED);
                return new ResponseEntity<>(responseDTO, HttpStatus.ALREADY_REPORTED);
            }
            /**
             * check if user already exist by username
             */
            log.info("About to check if username already exist");
            Optional<User> usernameExistOptional = userRepo.findUserByUsername(user.getUsername());
            if (usernameExistOptional.isPresent()){
                log.error("Username already exist:->>{}", user.getUsername());
                responseDTO = AppUtils.getResponseDto("Username already exist", HttpStatus.ALREADY_REPORTED);
                return new ResponseEntity<>(responseDTO, HttpStatus.ALREADY_REPORTED);
            }
            /**
             * save user record and return response on success
             */
            log.info("About to save new user record...");
            User userResponse = userRepo.save(user);
            log.info("User was created successfully");
            responseDTO = AppUtils.getResponseDto("User was created successfully", HttpStatus.CREATED, userResponse);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);

        }catch (Exception e) {
            log.error("Exception Occurred!, statusCode -> {} and Cause -> {} and Message -> {}", 500, e.getCause(), e.getMessage());
            ResponseDTO  response = AppUtils.getResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> findUserById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteUserById(UUID id) {
        return null;
    }
}
