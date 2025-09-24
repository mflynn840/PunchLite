package michael.PunchLiteDemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import michael.PunchLiteDemo.dto.AssignManagerRequest;
import michael.PunchLiteDemo.dto.SetWageRequest;
import michael.PunchLiteDemo.dto.UserUpdateRequest;
import michael.PunchLiteDemo.model.Role;
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

    public List<User> listUsersManagedBy(String managerUsername) {

        List<User> managedUsers = this.userRepo.findByManagerUsername(managerUsername)
                                    .orElseThrow(() -> new IllegalStateException("No users managed"));
        return managedUsers;
    }

    public void assignEmployeeToManager(AssignManagerRequest request) {
        User subordinate = this.userRepo.findByUsername(request.getUserUsername())
                                    .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

        //make sure the user is not already assigned to a manager
        if(subordinate.getManager() != null){throw new IllegalStateException("user already has a manager");}


        User manager = this.userRepo.findByUsername(request.getManagerUsername())
                                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        manager.addSubordinate(subordinate);
        subordinate.setManager(manager);
    }


    public void setWage(SetWageRequest request){

        //get the employee and requesting manager if they exist
        User employee = this.userRepo.findByUsername(request.getEmployeeUsername())
                                .orElseThrow(() -> new IllegalArgumentException());
        User manager = this.userRepo.findByUsername(request.getEmployeeUsername())
                                .orElseThrow(() -> new IllegalArgumentException());
        
        if(!manager.getSubordinates().contains(employee)){
            throw new IllegalStateException("Failed: This manager does not manage the employee");
        }

        employee.setHourlyRate(request.getNewHourlyRate());
        this.userRepo.save(employee);

    }

    public List<User> listEmployees() {
        return this.userRepo.findByRole(Role.EMPLOYEE)
            .orElse(new ArrayList<>());

    }

    public List<User> listManagers() {
        return this.userRepo.findByRole(Role.MANAGER)
                .orElse(new ArrayList<>());
    }

    public void updateHourlyRate(Long userId, SetWageRequest request) {
        // Get the employee to be updated
        User employee = this.userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        
        // Get the manager making the request
        User manager = this.userRepo.findByUsername(request.getManagerUsername())
                .orElseThrow(() -> new IllegalStateException("Manager not found"));
        
        // Security check: Verify that the manager actually manages this employee
        if (!manager.getSubordinates().contains(employee)) {
            throw new IllegalStateException("Access denied: You can only update wages for employees you manage");
        }
        
        // Validate the new hourly rate
        Double newHourlyRate = request.getNewHourlyRate();
        if (newHourlyRate == null) {
            throw new IllegalArgumentException("Hourly rate is required");
        }
        if (newHourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative");
        }
        
        // Update the employee's hourly rate
        employee.setHourlyRate(newHourlyRate);
        this.userRepo.save(employee);
    }
}
