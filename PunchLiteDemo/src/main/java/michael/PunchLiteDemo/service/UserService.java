package michael.PunchLiteDemo.service;

import java.util.List;


import org.springframework.stereotype.Service;

import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;
    
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public User getUserById(Long id){
        return this.userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getUserByUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    public User updateUser(Long id, User userDetails){
        User oldUser = this.userRepo.findById(id).orElseThrow(() -> new IllegalStateException("Error, user not found"));

        oldUser.setEmail(userDetails.getEmail());
        oldUser.setHourlyRate(userDetails.getHourlyRate());
        oldUser.setRole(userDetails.getRole());
        oldUser.setUsername(userDetails.getUsername());

        return this.userRepo.save(oldUser);
    }

    public void deleteUser(long userId){
        this.userRepo.deleteById(userId);
    }

    public List<User> listUsers(){
        return this.userRepo.findAll();
    }
}
