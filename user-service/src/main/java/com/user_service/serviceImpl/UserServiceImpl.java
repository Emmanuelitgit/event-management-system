package com.user_service.serviceImpl;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import com.user_service.repo.UserRepo;
import com.user_service.service.UserService;
import com.user_service.util.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @description This method is used to create a new user record
     * @param user the payload data of the user to be created
     * @return ResponseDTO containing the saved user record and status information
     * @author Emmanuel
     * @since 28th October 2025
     */

    @Override
    public ResponseDTO createUser(User user) {
        try {
            log.info("In create user method: {}", user);

            // Check if email already exists
            log.info("Checking if email already exists...");
            Optional<User> emailExistOptional = userRepo.findUserByEmail(user.getEmail());
            if (emailExistOptional.isPresent()) {
                log.warn("Email already exists: {}", user.getEmail());
                return AppUtils.getResponseDto("Email already exists", HttpStatus.CONFLICT);
            }

            // Check if username already exists
            log.info("Checking if username already exists...");
            Optional<User> usernameExistOptional = userRepo.findUserByUsername(user.getUsername());
            if (usernameExistOptional.isPresent()) {
                log.warn("Username already exists: {}", user.getUsername());
                return AppUtils.getResponseDto("Username already exists", HttpStatus.CONFLICT);
            }

            // Save user record
            log.info("Saving new user record...");
            User savedUser = userRepo.save(user);

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            log.info("Password encrypted successfully before saving user.");

            log.info("User created successfully with ID: {}", savedUser.getId());
            return AppUtils.getResponseDto("User was created successfully", HttpStatus.CREATED, savedUser);

        } catch (Exception e) {
            log.error("Exception occurred while creating user: {}", e.getMessage(), e);
            return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @description Update an existing user record by ID
     * @param user The updated user payload
     * @return ResponseDTO with the updated user record
     */

    @Override
    public ResponseDTO updateUser(User user) {
        try {
            log.info("In update user method for ID: {}", user.getId());

            if (user.getId() == null) {
                log.warn("User ID cannot be null for update");
                return AppUtils.getResponseDto("User ID cannot be null", HttpStatus.BAD_REQUEST);
            }

            Optional<User> existingUserOptional = userRepo.findById(user.getId());
            if (existingUserOptional.isEmpty()) {
                log.warn("User not found with ID: {}", user.getId());
                return AppUtils.getResponseDto("User not found with ID: {}", HttpStatus.NOT_FOUND);
            }

            User existingUser = existingUserOptional.get();

            //Update modifiable fields
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPhone(user.getPhone());

            User updatedUser = userRepo.save(existingUser);

            log.info("User updated successfully for ID: {}", updatedUser.getId());
            return AppUtils.getResponseDto("User updated successfully", HttpStatus.OK, updatedUser);

        }catch (Exception e){
           log.error("Error updating user: {}",e.getMessage(), e);
           return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @description List all users
     */

    @Override
    public ResponseDTO findAllUsers(int page, int size, String search) {
        try {
            log.info("Fetching all users with pagination. Page: {}, Size: {}, Search: {}", page, size, search);

            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

            Page<User> usersPage;

            // If a search term is provide, search by name or email

            if(search != null && !search.isBlank()){
                log.info("Searching users by term: {}", search);
                usersPage =  userRepo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
            }else{
                usersPage = userRepo.findAll(pageable);
            }

            if (usersPage.isEmpty()) {
                log.warn("No users found in database.");
                return AppUtils.getResponseDto("No users found", HttpStatus.NO_CONTENT);
            }

            log.info("Retrieved {} users successfully", usersPage.getNumberOfElements());
            return AppUtils.getResponseDto("Users retrieved successfully", HttpStatus.OK, usersPage);

        } catch (Exception e) {
            log.error("Error occurred while fetching users: {}", e.getMessage(), e);
            return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @description Find a user by ID
     * @param id UUID of the user
     * @return ResponseDTO with the found user record
     */
    @Override
    public ResponseDTO findUserById(UUID id) {
        try{
            log.info("Fetching user by ID: {}",id);
            Optional<User> userOptional = userRepo.findById(id);

            if(userOptional.isEmpty()){
                log.warn("User not found with ID: {}",id);
                return AppUtils.getResponseDto("User not found",HttpStatus.NOT_FOUND);

            }

            log.info("User found with ID: {}", id);
            return AppUtils.getResponseDto("User fetched successfully",HttpStatus.OK, userOptional.get());
        } catch (Exception e) {
            log.error("Error fetching user by ID:{}", e.getMessage(),e);
            return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        

    }

    /**
     * @description Delete a user record by ID
     * @param id UUID of the user to be deleted
     * @return ResponseDTO with deletion status
     */
    @Override
    public ResponseDTO deleteUserById(UUID id) {
        try{
            log.info("Attempting to delete user with ID:{}",id);

            Optional<User> userOptional = userRepo.findById(id);
            if(userOptional.isEmpty()){
                log.warn("User not found with ID: {} ", id);
                return AppUtils.getResponseDto("User not found",HttpStatus.NOT_FOUND);
            }

            userRepo.deleteById(id);
            log.info("User deleted successfully with ID: {}", id);
            return AppUtils.getResponseDto("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseDTO updateUserAddress(UUID id, String address) {
        try {
            log.info("Request received to update address for userId: {}", id);

            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isEmpty()) {
                log.warn("User not found for ID: {}", id);
                return AppUtils.getResponseDto("User not found", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();
            log.info("Updating address from '{}' to '{}'", user.getAddress(), address);

            user.setAddress(address);
            User updatedUser = userRepo.save(user);

            log.info("Address updated successfully for user ID: {}", id);
            return AppUtils.getResponseDto("Address updated successfully", HttpStatus.OK, updatedUser);

        } catch (Exception e) {
            log.error("Error updating address for user ID: {} | Message: {}", id, e.getMessage(), e);
            return AppUtils.getResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
