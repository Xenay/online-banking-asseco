package asseco.praksa.OnlineBank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("test") // Ensure this configuration is only active during tests
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Optionally disable CSRF, if not needed // Disable CSRF protection
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());

        return http.build();
    }
}
