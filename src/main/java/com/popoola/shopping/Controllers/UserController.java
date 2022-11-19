package com.popoola.shopping.Controllers;

import com.popoola.shopping.JWTsSecurity.JWT.JwtProvider;
import com.popoola.shopping.Models.DTOs.LoginDTO;
import com.popoola.shopping.Models.DTOs.UpdateDTO;
import com.popoola.shopping.Models.Response.JWTResponse;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;


@CrossOrigin
@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/register")
    public ResponseEntity<User> save( @Valid @RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
            return ResponseEntity.created(uri).body(userService.save(user));
//

    }

    @GetMapping("/get-users")
    public ResponseEntity<Object> getUsers(String role){
        return ResponseEntity.ok(userService.findByRole(role));
    }


    @PostMapping("/login")
    public ResponseEntity<JWTResponse> loginUser(@RequestBody LoginDTO loginDTO){
//        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generate(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findOne(userDetails.getUsername());
            JWTResponse res = new JWTResponse(user, jwt);
            return ResponseEntity.ok(res);
//        } catch (AuthenticationException ex){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
    }

    @GetMapping("/get-user-details/{email}")
    public ResponseEntity<User> getUserDetails(@PathVariable("email") String email, Principal principal) {
        if (principal.getName().equals(email)) {
            return ResponseEntity.ok(userService.findByEmail(email));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody UpdateDTO dto, Principal principal) {

        try {
            if (!principal.getName().equals(dto.getEmail())) throw new IllegalArgumentException();
            return ResponseEntity.ok(userService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
