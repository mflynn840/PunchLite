package michael.PunchLiteDemo.dto;

import java.time.LocalDateTime;

import michael.PunchLiteDemo.model.TimeEntry;

public class TimeEntryDto {
    

    private Long id;
    private Long userId;

    //non key attributes
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private Double durationHr;
    
    //no arg constructor
    public TimeEntryDto(TimeEntry t){
        this.id = t.getId();
        this.userId = t.getUser().getId();
        this.clockIn = t.getClockIn();
        this.clockOut = t.getClockOut();
        this.durationHr = t.getDurationHr();
    }

    //getter and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Long getUserId(){return userId;}
    public void setUserId(Long userId){this.userId = userId;}

    public LocalDateTime getClockIn(){return clockIn;}
    public void setClockIn(LocalDateTime clockIn){this.clockIn = clockIn;}

    public LocalDateTime getClockOut(){return clockOut;}
    public void setClockOut(LocalDateTime clockOut){this.clockOut = clockOut;}
    
    public Double getDurationHr(){return durationHr;}
    public void setDurationHr(Double durationHr){this.durationHr = durationHr;}
    


}
