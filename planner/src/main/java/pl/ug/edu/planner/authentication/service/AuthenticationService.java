package pl.ug.edu.planner.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ug.edu.planner.authentication.request.AuthenticationRequest;
import pl.ug.edu.planner.authentication.request.TokenRequest;
import pl.ug.edu.planner.authentication.response.AuthenticationResponse;
import pl.ug.edu.planner.authentication.request.RegisterRequest;
import pl.ug.edu.planner.jwt.service.JwtService;
import pl.ug.edu.planner.user.model.Role;
import pl.ug.edu.planner.user.model.User;
import pl.ug.edu.planner.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            return AuthenticationResponse
                    .builder()
                    .message("User already exists")
                    .build();

        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Registered")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse
                    .builder()
                    .message("Succesfully authenticate")
                    .token(jwtToken)
                    .build();
        } else {
            return AuthenticationResponse
                    .builder()
                    .message("There is no such user")
                    .build();
        }
    }

    public AuthenticationResponse IsTokenValid(TokenRequest request) {
        var token = request.getToken();
        var response = jwtService.isTokenValid(token);
        if (response){
            return AuthenticationResponse
                    .builder()
                    .message("Token is valid")
                    .token(token)
                    .build();
        } else {
            return AuthenticationResponse
                    .builder()
                    .message("Token is invalid")
                    .build();
        }
    }
}
