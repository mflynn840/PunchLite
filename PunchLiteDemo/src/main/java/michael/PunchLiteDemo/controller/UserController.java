package michael.PunchLiteDemo.controller;

import michael.PunchLiteDemo.dto.UserUpdateRequest;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.service.UserService;

import java.util.List;

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
    public List<User> getAllUsers(){
        return this.userService.listUsers();
    }

    //PUT /api/users/id
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long userId, @RequestBody UserUpdateRequest updateInfo){
        return this.userService.updateUser(userId, updateInfo);
    }

    //DELETE /api/users/id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId){
        this.userService.deleteUser(userId);
    } 

    //GET /api/user/id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId){
        return this.userService.getUserById(userId);
    }

}
