package com.user_service.repo;

import com.user_service.models.User;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.angus.mail.imap.protocol.UID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, UID> {
    Optional<User> findUserByEmail(@NotBlank(message = "Email cannot be null or empty") String email);

    Optional<User> findUserByUsername(@NotBlank(message = "Username cannot be null or empty") String username);
}
