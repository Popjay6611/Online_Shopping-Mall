package com.popoola.shopping.JWTsSecurity.JWT;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
                           HttpServletResponse response,
                           AuthenticationException ex)
        throws IOException {
        logger.error("Unauthorized error. Message - {}", ex.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }



}
