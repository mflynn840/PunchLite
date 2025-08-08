package michael.PunchLiteDemo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

//use jakarta imports for JPA
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class TimeEntry implements Serializable {
    
    //primary key
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //annotate object references to define foreign key relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //non key attributes
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private Double durationHr;

    private Boolean isValidated = false;
    
    //no arg constructor
    public TimeEntry(){}

    //getter and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public LocalDateTime getClockIn(){return clockIn;}
    public void setClockIn(LocalDateTime clockIn){this.clockIn = clockIn;}

    public LocalDateTime getClockOut(){return clockOut;}
    public void setClockOut(LocalDateTime clockOut){this.clockOut = clockOut;}
    
    public Double getDurationHr(){return durationHr;}
    public void setDurationHr(Double durationHr){this.durationHr = durationHr;}

    public void setIsValidated(Boolean isValidated) {this.isValidated = isValidated;}
    public Boolean getIsValidated() {return isValidated;}
    
}
