package com.popoola.shopping.JWTsSecurity.JWT;

import com.popoola.shopping.Models.User;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getToken(request);
        if (jwt != null && jwtProvider.validate(jwt)) {
            try {
              String userAcct = jwtProvider.getUserAccount(jwt);
              User user = userService.findOne(userAcct);
              // authenticate if jwt is ok
                SimpleGrantedAuthority sga = new SimpleGrantedAuthority((user.getRole()));
                ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
                list.add(sga);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, list);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                logger.error("Authentication failed");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer")){
            return authHeader.replace("Bearer", "");
        }

        return null;
    }
}
