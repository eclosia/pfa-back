package ma.eniad.pfaback.config;

import lombok.RequiredArgsConstructor;
import ma.eniad.pfaback.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(authorize -> authorize
                         .requestMatchers("/auth/**").permitAll()
                         .anyRequest().authenticated()
                 )
                 .sessionManagement(session -> session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
         ;

         return http.build();
    }

}
