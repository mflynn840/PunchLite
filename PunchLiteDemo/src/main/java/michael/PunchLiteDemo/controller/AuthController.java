package michael.PunchLiteDemo.controller;

import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import michael.PunchLiteDemo.dto.JwtResponse;
import michael.PunchLiteDemo.dto.LoginRequest;
import michael.PunchLiteDemo.dto.RegisterRequest;
import michael.PunchLiteDemo.service.AuthService;
import michael.PunchLiteDemo.service.UserService;


/**
 * Handle user authentication
 * 
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
    public void registerUser(@RequestBody RegisterRequest request){
        this.authService.registerUser(request);
        //call a service that validates input, checks if user exists, hashes password and saves user
        //return a success or error message
    }

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody LoginRequest request){
        //recieve un/password
        //pass to auth service that looks up user, verifies password, if valid generate a JWT or session token
        //return the token or an error
        return this.authService.loginUser(request);

    }


    //TODO: password resets
}
