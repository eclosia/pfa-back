package ma.eniad.pfaback.service;

import lombok.RequiredArgsConstructor;
import ma.eniad.pfaback.controller.auth.AuthRequest;
import ma.eniad.pfaback.controller.auth.AuthResponse;
import ma.eniad.pfaback.controller.auth.RegisterRequest;
import ma.eniad.pfaback.jwt.JwtUtil;
import ma.eniad.pfaback.model.enums.Role;
import ma.eniad.pfaback.repository.UserRepository;
import ma.eniad.pfaback.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build()
        ;
        userRepository.save(user);
        var token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build()
        ;
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build()
        ;
    }
}
