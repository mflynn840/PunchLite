package michael.PunchLiteDemo.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;


/**
 * Define a user data structure
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //attributes
    private String username;
    private String email;
    private String password;
    private String role;
    private Double hourlyRate;
    

    //Constructors
    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Getters and setters
    public Long getId() {return this.id;}
    protected void setId(Long id){this.id = id;}

    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}

    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}

    public String getRole(){return this.role;}
    public void setRole(String role){this.role = role;}

    public Double getHourlyRate(){return this.hourlyRate;}
    public void setHourlyRate(Double hourlyRate){this.hourlyRate = hourlyRate;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}

    //return a list of the users privledges
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
