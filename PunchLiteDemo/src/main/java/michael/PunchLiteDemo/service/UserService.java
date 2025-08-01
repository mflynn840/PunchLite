package michael.PunchLiteDemo.service;

import java.util.List;

import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.UserRepository;

public class UserService {
    
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public User createUser(User user){
        //see if the user already exists
        if(userRepo.existsById(user.getId()) || userRepo.existsByUsername(user.getUsername())){
            throw new IllegalStateException("Error: User already exists");
        }
        return this.userRepo.save(user);
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
