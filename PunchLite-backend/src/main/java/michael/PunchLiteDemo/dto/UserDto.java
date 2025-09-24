package michael.PunchLiteDemo.dto;

import michael.PunchLiteDemo.model.Role;
import michael.PunchLiteDemo.model.User;

public class UserDto {
    private Long id;
    private String username;
    private Role role;
    private Double hourlyRate;

    public UserDto(User u){
        setId(u.getId());
        setUsername(u.getUsername());
        setRole(u.getRole());
        setHourlyRate(u.getHourlyRate());
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



    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
