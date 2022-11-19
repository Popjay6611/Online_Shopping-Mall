package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepo extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);
    Collection<User> findAllByRole(String role);
}
