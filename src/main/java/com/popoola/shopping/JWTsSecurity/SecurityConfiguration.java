package com.popoola.shopping.JWTsSecurity;

import com.popoola.shopping.JWTsSecurity.JWT.JWTFilter;
import com.popoola.shopping.JWTsSecurity.JWT.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@DependsOn("passwordEncoder")
public class SecurityConfiguration {
    @Autowired
    JWTFilter jwtFilter;

    @Autowired
    private JwtEntryPoint accessDenyHandler;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("$(spring.queries.roles-query)")
    private String rolesQuery;
//
//    @Bean
//    public UserDetailsManager users(){
//        UserDetails user = User.withUserDetails()
//                .passwordEncoder(passwordEncoder)
//                .password(passwordEncoder.encode("password"))
//                .authorities(rolesQuery)
//                .build();
//
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
}
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user = User.withUsername(usersQuery)
//                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
//                .authorities(rolesQuery)
//                .roles(rolesQuery)
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.headers().frameOptions().sameOrigin();
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers("/account/update-user").authenticated()
                    .antMatchers("/account/get-user-details/{email}").authenticated()
                    .antMatchers("/cart/**").authenticated()
                    .antMatchers("/order/finish/**").access("hasAnyAuthority('STAFF','CEO', 'CUSTOMER')")
                    .antMatchers("/cart/**").authenticated()
                    .antMatchers("/category/add-category").access("hasAnyAuthority('STAFF','CEO', 'CUSTOMER')")
                    .antMatchers("/order/**").authenticated()
                    .antMatchers("/product/add-product/**").access("hasAnyAuthority('STAFF','CEO', 'CUSTOMER')")
                    .antMatchers("/product/get-products").authenticated()
                    .antMatchers("/ws/**", "/ws/info?t=").permitAll()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().permitAll()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(accessDenyHandler)
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }


}
