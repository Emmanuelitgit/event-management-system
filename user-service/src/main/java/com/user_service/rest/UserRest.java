package com.user_service.rest;

import com.user_service.dto.ResponseDTO;
import com.user_service.models.User;
import com.user_service.serviceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRest {
    private final UserServiceImpl userService;

    @Autowired
    public UserRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "This endpoint is used to save a new user record")
    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@RequestBody @Valid User user){
        return userService.createUser(user);
    }
}
