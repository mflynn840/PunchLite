package michael.PunchLiteDemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import michael.PunchLiteDemo.dto.UserUpdateRequest;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.TimeEntryRepository;
import michael.PunchLiteDemo.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepo;
    private final TimeEntryRepository timeEntryRepo;
    
    public UserService(UserRepository userRepo, TimeEntryRepository timeEntryRepo){
        this.userRepo = userRepo;
        this.timeEntryRepo = timeEntryRepo;
    }

    public User getUserById(Long id){
        return this.userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getUserByUsername(String username){
        return this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /** Allow partial user updates  by picking non-null between old entry and update DTO */
    public void mergeNonNullFields(UserUpdateRequest dto, User entity) {
        Optional.ofNullable(dto.getUsername()).ifPresent(entity::setUsername);
        Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(dto.getRole()).ifPresent(entity::setRole);
        Optional.ofNullable(dto.getHourlyRate()).ifPresent(entity::setHourlyRate);
    }

    public User updateUser(Long id, UserUpdateRequest userDetails){
        User oldUser = this.userRepo.findById(id).orElseThrow(() -> new IllegalStateException("Error, user not found"));

        oldUser.setEmail(userDetails.getEmail());
        oldUser.setHourlyRate(userDetails.getHourlyRate());
        oldUser.setRole(userDetails.getRole());
        oldUser.setUsername(userDetails.getUsername());

        return this.userRepo.save(oldUser);
    }

    public void deleteUser(Long userId){
        this.userRepo.deleteById(userId);
    }

    public List<User> listUsers(){
        return this.userRepo.findAll();
    }

    public Boolean isClockedIn(String username){
        TimeEntry latestEntry = this.timeEntryRepo.findTopByUser_UsernameOrderByClockInDesc(username);
        //the employees is clocked in but didnt clock out on their most recent punch
        return latestEntry != null && latestEntry.getClockOut() == null;
    }
}
