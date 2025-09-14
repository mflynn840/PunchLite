package michael.PunchLiteDemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private Double hourlyRate;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntry> timeEntries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role; 

    //Relationship between employees and managers
    @OneToMany(mappedBy = "manager", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<User> subordinates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

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

    public Role getRole(){return this.role;}
    public void setRole(Role role){this.role = role;}

    public Double getHourlyRate(){return this.hourlyRate;}
    public void setHourlyRate(Double hourlyRate){this.hourlyRate = hourlyRate;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){
        this.password = password;
    }

    public List<User> getSubordinates(){return this.subordinates;}
    public void setSubordinates(List<User> subordinates){this.subordinates = subordinates;}

    public List<TimeEntry> getTimeEntries(){return this.timeEntries;}
    public void setTimeEntries(List<TimeEntry> timeEntries){this.timeEntries=timeEntries;}


    public void addTimeEntry(TimeEntry t){
        this.timeEntries.add(t);
    }
    public void addSubordinate(User subordinate){
        this.subordinates.add(subordinate);
    
    }
    public void removeSubordinate(User subordinate) {
    if (this.subordinates != null) {
        this.subordinates.remove(subordinate);
        subordinate.setManager(null);
    }
}
    public User getManager(){return this.manager;}
    public void setManager(User manager){this.manager=manager;}

    //return a list of the users privledges
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
