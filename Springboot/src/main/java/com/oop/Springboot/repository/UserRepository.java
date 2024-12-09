package com.oop.Springboot.repository;

import com.oop.Springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link User} entities in the database.
 * This interface extends {@link JpaRepository} to provide CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} by their username.
     *
     * @param username the username of the user to find
     * @return the {@link User} with the given username or null if no such user exists
     */
    User findByUsername(String username);
}
