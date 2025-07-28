package michael.PunchLiteDemo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;

public class TimeEntry {
    
    //primary key
    @Id @GeneratedValue
    private Long id;

    //attributes
    private User user;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private float durationHr;
    
    //getter and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public LocalDateTime getClockIn(){return clockIn;}
    public void setClockIn(LocalDateTime clockIn){this.clockIn = clockIn;}

    public LocalDateTime getClockOut(){return clockOut;}
    public void setClockOut(LocalDateTime clockOut){this.clockOut = clockOut;}
    
    public float getDurationHr(){return durationHr;}
    public void setDurationHr(float durationHr){this.durationHr = durationHr;}
    
}
