package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/login").permitAll() // Cho phép truy cập trang chính & login
                .requestMatchers("/admin").hasRole("ADMIN") // Chỉ Admin mới truy cập /admin
                .requestMatchers("/hello").hasRole("USER") // User vào /hello
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Trang đăng nhập
                .successHandler(customLoginSuccessHandler()) // Xử lý đăng nhập thành công
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout") // URL logout
                .logoutSuccessUrl("/login?logout") // Chuyển hướng về login sau khi logout
                .invalidateHttpSession(true) // Xóa session
                .deleteCookies("JSESSIONID") // Xóa cookie đăng nhập
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
}
