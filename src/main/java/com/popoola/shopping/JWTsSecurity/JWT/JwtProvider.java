package com.popoola.shopping.JWTsSecurity.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpiration}")
    private int jwtExpiration;

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generate(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(key)

                .compact();
    }
    public boolean validate(String jwt){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e){
            logger.error("JWT Authentication failed");
        }
        return false;
    };

   public String getUserAccount(String jwt){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt)
                .getBody().getSubject();
   };
}
