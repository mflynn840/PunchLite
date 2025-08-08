package michael.PunchLiteDemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TimeEntry> getTimeEntriesByUser(String username, int limit){
        List<TimeEntry> allEntries = this.timeEntryRepo.findByUserUsernameOrderByClockInDesc(username);
        return allEntries.stream()
                        .limit(limit)
                        .collect(Collectors.toList());

    }

    public TimeEntry handleClockIn(String username){

        //get the user
        User u = this.userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        TimeEntry latestEntry = this.timeEntryRepo.findTopByUser_UsernameOrderByClockInDesc(username);

        //if the employees is already clocked in
        if(latestEntry != null && latestEntry.getClockOut() == null){
            throw new IllegalStateException("Error: employee is already clocked in");
        }
        TimeEntry newPunch = new TimeEntry();
        newPunch.setUser(u);
        newPunch.setClockIn(LocalDateTime.now());
        return this.timeEntryRepo.save(newPunch);

    }

    public TimeEntry handleClockOut(String username){

        TimeEntry latestEntry = this.timeEntryRepo.findTopByUser_UsernameOrderByClockInDesc(username);
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
