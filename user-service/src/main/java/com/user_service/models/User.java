package com.user_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;
    @NotBlank(message = "Email cannot be null or empty")
    private String email;
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotBlank(message = "Password cannot be null or empty")
    private String password;
    @NotBlank(message = "Phone cannot be null or empty")
    @Size(max = 10, min = 10, message = "Phone should not less than 10 or more than 10")
    private String phone;
}
