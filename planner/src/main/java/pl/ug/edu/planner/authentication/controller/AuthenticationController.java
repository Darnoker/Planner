package pl.ug.edu.planner.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.edu.planner.authentication.request.AuthenticationRequest;
import pl.ug.edu.planner.authentication.request.TokenRequest;
import pl.ug.edu.planner.authentication.response.AuthenticationResponse;
import pl.ug.edu.planner.authentication.service.AuthenticationService;
import pl.ug.edu.planner.authentication.request.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws Exception {
        AuthenticationResponse response = authenticationService.register(request);
        if(response.getMessage().equals("User already exists")) {
            return ResponseEntity.status(409).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = authenticationService.authenticate(request);
        if(response.getMessage().equals("There is no such user")) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/IsTokenValid")
    public ResponseEntity<AuthenticationResponse> isTokenValid(@RequestBody TokenRequest request) {
        AuthenticationResponse response = authenticationService.IsTokenValid(request);
        if(response.getMessage().equals("Token is invalid")){
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
