package michael.PunchLiteDemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import michael.PunchLiteDemo.dto.JwtResponse;
import michael.PunchLiteDemo.dto.LoginRequest;
import michael.PunchLiteDemo.dto.RegisterRequest;
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

        String username = newUserRequest.getUsername();
        String rawPassword = newUserRequest.getPassword();
        String email = newUserRequest.getEmail();

        //see if the user already exists
        if(userRepo.existsByUsername(username)){
            throw new IllegalStateException("Error: User already exists");
        }

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

        User user = this.userRepo.findByUsername(username);
        if(user == null){
            throw new IllegalStateException("Error: user does not exist");
        }
        
        //authenticate correct password using bCrypt
        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new IllegalArgumentException("Error: incorrect password");
        }

        //generate and return users JWT authentication token (1day)

        String token = this.jwtUtil.generateToken(username);
        return new JwtResponse(token);
    }
}
