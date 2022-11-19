package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Exceptions.ShoppingNotFound;
import com.popoola.shopping.Models.Cart;
import com.popoola.shopping.Models.Chats;
import com.popoola.shopping.Models.DTOs.UpdateDTO;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Models.Wishlists;
import com.popoola.shopping.Repos.CartRepo;
import com.popoola.shopping.Repos.ChatRepo;
import com.popoola.shopping.Repos.UserRepo;
import com.popoola.shopping.Repos.WishListRepo;
import com.popoola.shopping.Servuces.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserService implements IUserService, UserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    WishListRepo wishListRepo;

    @Override
    public User findOne(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Collection<User> findByRole(String role) {
        return userRepo.findAllByRole(role);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepo.save(user);

            // Initial Cart
            Cart savedCart = cartRepo.save(new Cart(savedUser));
            savedUser.setCart(savedCart);

            // Initial Message
            Chats savedChat = chatRepo.save(new Chats(savedUser));
            savedUser.setChats(savedChat);

            // Initial Wishlist
            Wishlists savedWishlist = wishListRepo.save(new Wishlists(savedUser));
            savedUser.setWishlists(savedWishlist);

            return userRepo.save(savedUser);
        } catch (Exception ex)
        {
            throw new ShoppingException(ResponseEnum.VALID_ERROR);
        }
    }

    @Override
    @Transactional
    public User update(UpdateDTO dto) {
        User oldUser = userRepo.findByEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty())
        {
            oldUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUsername() != null && !dto.getUsername().isEmpty())
        {
            oldUser.setUsername(dto.getUsername());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty())
        {
            oldUser.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAddress() != null && !dto.getAddress().isEmpty())
        {
            oldUser.setAddress(dto.getAddress());
        }

        return userRepo.save(oldUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null){
            throw new ShoppingNotFound("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
