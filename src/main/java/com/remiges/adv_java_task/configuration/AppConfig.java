package com.remiges.adv_java_task.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.remiges.adv_java_task.json.LoggingInterceptor;

//Problem 25. Implement Logging Interceptor in Spring MVC Application
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }


     @Bean
    public UserDetailsService  userDetailsService() {   
        
       UserDetails user = User.builder().username("shivendrak")
        .password(passwordEncoder().encode("abcd")).roles("admin").build();
    //     // ("Shivendra@remiges")
        

        // UserDetails user1 = User.builder().username("deepak")
        // .password(passwordEncoder().encode("abc"))
        // .roles("admin").build();

        return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
