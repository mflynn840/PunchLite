package michael.PunchLiteDemo.dto;

public class JwtResponse {
    private String token;
    private UserDto user;

    public JwtResponse(String token, UserDto user){
        this.token = token;
        this.user = user;
    }

    public String getToken(){return this.token;}
    public void setToken(String token){this.token = token;}
    
    public UserDto getUser(){return this.user;}
    public void setUser(UserDto user){this.user = user;}
}
