package io.security.springsecuritymaster.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest()
                                               .authenticated())
            //formLogin()
            .formLogin(form -> form
//                    .loginPage("/loginPage")
.loginProcessingUrl("/loginProc")
.defaultSuccessUrl("/", false)
.failureUrl("/failed")
.usernameParameter("userId")
.passwordParameter("passwd")
.successHandler((request, response, authentication) -> {
    System.out.println("authentication : " + authentication);
    response.sendRedirect("/home");
})
.failureHandler((request, response, exception) -> {
    System.out.println("exception : " + exception.getMessage());
    response.sendRedirect("/login");
})
.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userA = User.withUsername("userA")
                                .password("{noop}1234")
                                .roles("USER")
                                .build();

        UserDetails userB = User.withUsername("userB")
                                .password("{noop}1234")
                                .roles("USER")
                                .build();

        UserDetails admin = User.withUsername("admin")
                                .password("{noop}1234")
                                .roles("ADMIN")
                                .build();
        return new InMemoryUserDetailsManager(userA, userB, admin);
    }

}
