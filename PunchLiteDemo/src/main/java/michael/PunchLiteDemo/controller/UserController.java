package michael.PunchLiteDemo.controller;

import michael.PunchLiteDemo.dto.UserDto;
import michael.PunchLiteDemo.dto.UserUpdateRequest;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

    //-------EXPOSES REST API HERE-----
    //GET /api/users
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(this.userService.listUsers());
    }

    //PUT /api/users/id
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
        //Add it to a JSON
        Map<String, Boolean> response = Map.of("clockedIn", clockedIn);
        return ResponseEntity.ok(response);
    }

}
