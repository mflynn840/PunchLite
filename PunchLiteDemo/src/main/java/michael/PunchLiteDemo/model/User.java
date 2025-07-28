package michael.PunchLiteDemo.model;

import jakarta.persistence.*;


/**
 * Define a user database entry by extending entity
 * 
 */


@Entity
public class User {

    //Define all attributes of the SQL table
    //Use tags to set SQL constraints
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String username;
    private String email;

    //Create getters and setters for each attribute
    public Long getId() {return id;}
    public void setId(Long id){this.id = id;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}


}
