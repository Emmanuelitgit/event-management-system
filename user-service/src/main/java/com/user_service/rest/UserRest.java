package com.user_service.rest;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import com.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserRest {

    private final UserService userService;

    public UserRest(UserService userService) {
        this.userService = userService;
    }

    //Create a new user record
    @Operation(summary = "This endpoint is used to save a new user record")
    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@RequestBody @Valid User user) {
        ResponseDTO response = userService.createUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    //Retrieve all user records
    @Operation(summary = "This endpoint is used to retrieve all users in the system")
    @GetMapping
    public ResponseEntity<ResponseDTO> findAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search)
    {

        ResponseDTO response = userService.findAllUsers(page, size, search);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    //  Find user by ID
     @Operation(summary = "This endpoint is used to find a user by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findUserById(@PathVariable UUID id) {
        ResponseDTO response = userService.findUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


   //Update a user's details
    @Operation(summary = "This endpoint is used to update an existing user record")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid User user){
        //ensure we are updating the right user by assigning the path ID
        user.setId(id);
        ResponseDTO response  = userService.updateUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Delete user by ID.
    @Operation(summary = "This endpoint is used to delete a record by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable UUID id){
        ResponseDTO response = userService.deleteUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Update only the user's address(partial update)
    @Operation(summary = "This endpoint is use to update user's address by ID")
    @PatchMapping("/{id}/address")
    public ResponseEntity<ResponseDTO> updatedUserAddress(@PathVariable UUID id, @RequestParam String address){
        ResponseDTO response = userService.updateUserAddress(id, address);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
