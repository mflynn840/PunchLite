package michael.PunchLiteDemo.dto;


import michael.PunchLiteDemo.model.User;

public class UserDto {
    private Long id;
    private String username;
    private String role;

    public UserDto(User u){
        setId(u.getId());
        setUsername(u.getUsername());
        setRole(u.getRole());

    }
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }



    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }
}
