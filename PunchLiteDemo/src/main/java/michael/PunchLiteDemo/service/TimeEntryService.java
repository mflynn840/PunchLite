package michael.PunchLiteDemo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import michael.PunchLiteDemo.repository.TimeEntryRepository;
import michael.PunchLiteDemo.repository.UserRepository;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.model.User;

@Service
public class TimeEntryService {
    
    //inject db
    private final TimeEntryRepository timeEntryRepo;
    private final UserRepository userRepo;
    
    public TimeEntryService(TimeEntryRepository timeEntryRepo, UserRepository userRepo){
        this.timeEntryRepo = timeEntryRepo;
        this.userRepo = userRepo;
    }

    public List<TimeEntry> getTimeEntriesByUser(Long userId){
        return this.timeEntryRepo.findByUserId(userId);
    }

    public TimeEntry handleClockIn(Long userId){

        //get the user
        User u = this.userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        TimeEntry latestEntry = this.timeEntryRepo.findTopByUserIdOrderByClockInDesc(userId);

        //if the employees is already clocked in
        if(latestEntry != null && latestEntry.getClockOut() == null){
            throw new IllegalStateException("Error: employee is already clocked in");
        }
        TimeEntry newPunch = new TimeEntry();
        newPunch.setUser(u);
        newPunch.setClockIn(LocalDateTime.now());
        return this.timeEntryRepo.save(newPunch);

    }

    public TimeEntry handleClockOut(Long userId){

        TimeEntry latestEntry = this.timeEntryRepo.findTopByUserIdOrderByClockInDesc(userId);
        //if the employee is not clocked in
        if(latestEntry == null || latestEntry.getClockOut() != null){
            throw new IllegalStateException("Error: employee is not clocked in");
        }

        LocalDateTime clockIn = latestEntry.getClockIn();
        LocalDateTime clockOut = LocalDateTime.now();
        latestEntry.setClockOut(clockOut);

        double durationHours = java.time.Duration.between(clockIn, clockOut).toMinutes()/60.0;
        latestEntry.setDurationHr(durationHours);
        return this.timeEntryRepo.save(latestEntry);
        
    }
}
