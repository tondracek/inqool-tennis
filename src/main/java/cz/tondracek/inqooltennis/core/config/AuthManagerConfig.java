package cz.tondracek.inqooltennis.core.config;

import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthManagerConfig {

    private final UserRepository userRepository;

    public AuthManagerConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email);
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.email())
                    .password(user.passwordHash())
                    .roles(user.role().name())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return cz.tondracek.inqooltennis.common.password.PasswordEncoder.getEncoder();
    }
}
