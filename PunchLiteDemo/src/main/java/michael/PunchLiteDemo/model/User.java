package michael.PunchLiteDemo.model;

import jakarta.persistence.*;


/**
 * Define a user data structure
 */


@Entity
public class User {

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    //attributes
    private String username;
    private String email;
    private String role;
    private float hourlyRate;

    //Create getters and setters for each attribute
    public Long getId() {return id;}
    public void setId(Long id){this.id = id;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getRole(){return role;}
    public void setRole(String role){this.role = role;}

    public float getHourlyRate(){return hourlyRate;}
    public void setHourlyRate(float hourlyRate){this.hourlyRate = hourlyRate;}
}
