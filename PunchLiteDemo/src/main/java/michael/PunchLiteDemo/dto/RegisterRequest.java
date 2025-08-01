package michael.PunchLiteDemo.dto;

public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    public void setEmail(String email){this.email = email;}
    public String getEmail(){return this.email;}

    public void setUsername(String username){this.username = username;}
    public String getUsername(){return this.username;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    
}
