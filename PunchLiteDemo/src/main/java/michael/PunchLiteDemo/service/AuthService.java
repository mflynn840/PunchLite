package michael.PunchLiteDemo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.InvalidClaimException;
import michael.PunchLiteDemo.dto.JwtResponse;
import michael.PunchLiteDemo.dto.LoginRequest;
import michael.PunchLiteDemo.dto.RegisterRequest;
import michael.PunchLiteDemo.exception.InvalidCredentialsException;
import michael.PunchLiteDemo.exception.UserAlreadyExistsException;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.UserRepository;
import michael.PunchLiteDemo.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(RegisterRequest newUserRequest){

        //Throw exception when user already exists
        if(userRepo.existsByUsername(newUserRequest.getUsername())){
            throw new UserAlreadyExistsException("Username already taken");
        }

        String username = newUserRequest.getUsername();
        String rawPassword = newUserRequest.getPassword();
        String email = newUserRequest.getEmail();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(this.passwordEncoder.encode(rawPassword));
        newUser.setEmail(email);
        this.userRepo.save(newUser);
    }

    /** authenticate user using hashed password, return a JWT token DTO*/
    public JwtResponse loginUser(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String rawPassword = loginRequest.getPassword();

        User user = this.userRepo.findByUsername(username)
                        .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        
        //authenticate correct password using bCrypt
        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new InvalidCredentialsException("Invalid username or password");
        }

        //generate and return users JWT authentication token (1day)

        String token = this.jwtUtil.generateToken(username);
        return new JwtResponse(token);
    }
}
