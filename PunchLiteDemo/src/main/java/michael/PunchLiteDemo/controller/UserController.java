package michael.PunchLiteDemo.controller;

import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.UserRepository;

import java.util.List;

import org.springframework.web.bind.annotation.*;

/** 
 * Define Logic to interact with the User database
 * JPA simplifies SQL queries
*/

@RestController //This class converts HTTP requests to serialized JSONs
@RequestMapping("/users") //all HTTP endpoints that this covers are under /users
public class UserController {
    
    //set db connection in the contructor
    private final UserRepository repo;

    public UserController(UserRepository repo){

        this.repo = repo;
    }

    //handle a GET request at the controllers base path
    @GetMapping
    public List<User> getAllUsers(){
        return repo.findAll();
    }

    //handle a GET request at the /id

    //handle a POST request to /users
    // -deserialize the incoming JSON (@RequestBody)
    // -insert the user into the database
    // -return the saved user (serialized)
    @PostMapping
    public User createUser(@RequestBody User user){
        return repo.save(user);
    }

}
