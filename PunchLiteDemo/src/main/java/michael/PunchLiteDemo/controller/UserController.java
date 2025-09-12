package michael.PunchLiteDemo.controller;

import michael.PunchLiteDemo.dto.ApiResponse;
import michael.PunchLiteDemo.dto.AssignManagerRequest;
import michael.PunchLiteDemo.dto.SetWageRequest;
import michael.PunchLiteDemo.dto.UserDto;
import michael.PunchLiteDemo.dto.UserUpdateRequest;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** 
 * UserController interacts with UserService to
 * Expose the REST API for User CRUD operations
 * 
 * - Create Users
 * - Delete Users
 * - Modify Users
*/

@RestController //This class converts HTTP requests to serialized JSONs
@RequestMapping("/api/users") //all HTTP endpoints that this covers are under /api/users endpoints
public class UserController {
    
    //save the service layer
    private final UserService userService;

    public UserController(UserService userService){

        this.userService = userService;
    }

    //GET /api/users
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> allUsers = this.userService.listUsers();
        return ResponseEntity.ok(wrapUsers(allUsers));
    }

    // GET /api/users/employees
    @GetMapping("/employees")
    public ResponseEntity<List<UserDto>> getAllEmployees(){
        List<User> allEmployees = this.userService.listEmployees();
        System.out.println(allEmployees.size());
        return ResponseEntity.ok(wrapUsers(allEmployees));
    }

    // GET /api/users/managers
    @GetMapping("/managers")
    public ResponseEntity<List<UserDto>> getAllManagers(){
        List<User> allManagers = this.userService.listManagers();
        return ResponseEntity.ok(wrapUsers(allManagers));
    }

    //GET /api/users/managed-by/manager-id
    @GetMapping("/managed-by/{username}")
    public ResponseEntity<List<UserDto>> getUsersManagedBy(@PathVariable("username") String managerUsername){
        List<User> users = this.userService.listUsersManagedBy(managerUsername);
        return ResponseEntity.ok(wrapUsers(users));
    }

    //PUT /api/users/id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserUpdateRequest updateInfo){
        User updatedU = this.userService.updateUser(userId, updateInfo);
        return ResponseEntity.ok(new UserDto(updatedU));
    }

    //DELETE /api/users/id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId){
        this.userService.deleteUser(userId);
    } 


    @GetMapping("/clock-status/{username}")
    public ResponseEntity<Map<String,Boolean>> isClockedIn(@PathVariable("username") String username){
        Boolean clockedIn = this.userService.isClockedIn(username);
        //map it to a JSON
        Map<String, Boolean> response = Map.of("clockedIn", clockedIn);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/assign-manager")
    public ResponseEntity<ApiResponse> assignEmployeeToManager(@RequestBody AssignManagerRequest request){
        this.userService.assignEmployeeToManager(request);
        return ResponseEntity.ok(new ApiResponse("Success"));
    }


    /**
     * Set the employees wage if you are their manager
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/setWage")
    public ResponseEntity<?> setWage(@RequestBody SetWageRequest request){

        try{
            this.userService.setWage(request);
            return ResponseEntity.ok(new ApiResponse("Success"));
        }catch(IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** Convert a list of users to a list of userDTOs */
    public List<UserDto> wrapUsers(List<User> users){
        return users.stream().map(UserDto::new).toList();
    }

}
