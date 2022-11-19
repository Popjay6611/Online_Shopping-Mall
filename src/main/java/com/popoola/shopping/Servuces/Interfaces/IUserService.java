package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.DTOs.UpdateDTO;
import com.popoola.shopping.Models.User;

import java.util.Collection;

public interface IUserService {
    User findOne(String username);

    User findByEmail(String email);
    Collection<User> findByRole(String role);
    User save(User user);
    User update(UpdateDTO dtor);
}
