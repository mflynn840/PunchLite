package michael.PunchLiteDemo.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import michael.PunchLiteDemo.dto.TimeEntryDto;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.service.TimeEntryService;



/**
 * Handle CRUD operations for time entries
 * -Clock in
 * -clock out
 * -list time entries for user
 */
@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {
    
    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService){
        this.timeEntryService = timeEntryService;
    }

    //REST API here
    @GetMapping("/employees/{username}/get-time-entries")
    public ResponseEntity<List<TimeEntryDto>> getTimeEntriesByEmployee(@PathVariable("username") String username,
                                                                        @RequestParam(value="limit", defaultValue="5") int limit){
        List<TimeEntry> entries = this.timeEntryService.getTimeEntriesByUser(username, limit);

        //convert list of time entries into list of time entry dtos
        List<TimeEntryDto> dtos = entries.stream()
                                        .map(TimeEntryDto::new)
                                        .toList();
        return ResponseEntity.ok(dtos);
    }


    @PostMapping("/employees/{username}/clock-in")
    public ResponseEntity<TimeEntryDto> clockIn(@PathVariable("username") String username){
        TimeEntry res = this.timeEntryService.handleClockIn(username);
        return ResponseEntity.ok(new TimeEntryDto(res));
    }

    @PostMapping("/employees/{username}/clock-out")
    public ResponseEntity<TimeEntryDto> clockOut(@PathVariable("username") String username){
        TimeEntry res = this.timeEntryService.handleClockOut(username);
        return ResponseEntity.ok(new TimeEntryDto(res));
    }  

    /**
     * Validate a time entry
     * - Only the correct manager can validate time entries
     * - The time entry must have a clock in and clock out time
     * - The time entry being modified is being delivered as JSON in the request body
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/managers/{id}/validate")
    public ResponseEntity<TimeEntryDto> validateTimeEntry(@PathVariable("id") Long managerId, @RequestBody TimeEntryDto timeEntry){

        TimeEntry validated = this.timeEntryService.validateTimeEntry(managerId, timeEntry);
        return ResponseEntity.ok(new TimeEntryDto(validated));
    }

}
