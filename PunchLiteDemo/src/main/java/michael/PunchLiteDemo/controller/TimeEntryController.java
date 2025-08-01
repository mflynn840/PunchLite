package michael.PunchLiteDemo.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.service.TimeEntryService;



/**
 * 
 * 
 */
@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {
    
    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService){
        this.timeEntryService = timeEntryService;
    }

    //REST API here
    @GetMapping("/employees/{id}/time-entries")
    public ResponseEntity<List<TimeEntry>> getTimeEntriesByEmployee(@PathVariable("id") Long userId){
        return ResponseEntity.ok(this.timeEntryService.getTimeEntriesByUser(userId));
    }


    @PostMapping("/employees/{id}/clock-in")
    public TimeEntry clockIn(@PathVariable("id") Long userId){
        return this.timeEntryService.handleClockIn(userId);
    }

    @PostMapping("/employees/{id}/clock-out")
    public TimeEntry clockOut(@PathVariable("id") Long userId){
        return this.timeEntryService.handleClockOut(userId);
    }  


}
