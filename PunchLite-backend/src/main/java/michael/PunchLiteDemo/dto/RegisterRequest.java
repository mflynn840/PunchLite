package michael.PunchLiteDemo.dto;
import michael.PunchLiteDemo.model.Role;

public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Role role;

    public void setEmail(String email){this.email = email;}
    public String getEmail(){return this.email;}

    public void setUsername(String username){this.username = username;}
    public String getUsername(){return this.username;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setRole(Role role){this.role = role;}
    public Role getRole(){return this.role;}
    
}
