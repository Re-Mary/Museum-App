package com.museum.app.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user)));
    }

    @GetMapping("/public/health")
    public ResponseEntity<String> publicHealth() {
        return ResponseEntity.ok("museum-backend-ok");
    }

    @GetMapping("/secure/me")
    public ResponseEntity<String> secureMe(Authentication authentication) {
        return ResponseEntity.ok("Authenticated as " + authentication.getName());
    }
}
