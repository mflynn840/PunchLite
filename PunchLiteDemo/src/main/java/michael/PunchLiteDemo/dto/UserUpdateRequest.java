package michael.PunchLiteDemo.dto;

import michael.PunchLiteDemo.model.Role;

public class UserUpdateRequest {
    
    private String username;
    private String email;
    private Role role;
    private Double hourlyRate;  // Use wrapper Double to allow null

    // No-arg constructor
    public UserUpdateRequest() {}

    // All-args constructor (optional)
    public UserUpdateRequest(String username, String email, Role role, Double hourlyRate) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return this.role;
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
