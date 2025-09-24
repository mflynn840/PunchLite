package michael.PunchLiteDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import michael.PunchLiteDemo.dto.ApiResponse;
import michael.PunchLiteDemo.dto.JwtResponse;
import michael.PunchLiteDemo.dto.LoginRequest;
import michael.PunchLiteDemo.dto.RegisterRequest;
import michael.PunchLiteDemo.service.AuthService;



/**
 * Handle user authentication using JWT
 * -Login
 * -Register
 * 
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService){
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        this.authService.registerUser(registerRequest);
        return ResponseEntity.ok(new ApiResponse("User succesfully registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest request){
        JwtResponse token = this.authService.loginUser(request);
        return ResponseEntity.ok(token);

    }


    //TODO: password resets
}
