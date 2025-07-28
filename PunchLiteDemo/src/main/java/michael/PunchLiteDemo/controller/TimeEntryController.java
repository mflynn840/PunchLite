package michael.PunchLiteDemo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.repository.TimeEntryRepository;

@RestController
@RequestMapping("/api/time-entry")
public class TimeEntryController {
    
    private TimeEntryRepository repo;

    public TimeEntryController(TimeEntryRepository repo){
        this.repo = repo;
    }

    //REST API here
    @GetMapping
    public List<TimeEntry> getAllTimeEntries(){
        return this.repo.findAll();
    }

    @PostMapping
    public TimeEntry clockIn(@RequestBody TimeEntry entry){
        entry.setClockIn(LocalDateTime.now());
        return repo.save(entry);
    }

    @PostMapping
    public TimeEntry clockOut(@RequestBody TimeEntry entry){
        entry.setClockOut(LocalDateTime.now());
        return repo.save(entry);
    }  


}
