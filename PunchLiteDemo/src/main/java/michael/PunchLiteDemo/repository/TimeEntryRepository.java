package michael.PunchLiteDemo.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.model.User;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long>{
    List<TimeEntry> findByUserId(Long userId);
    List<TimeEntry> findByClockInAfterAndUser(LocalDateTime clockIn, User user);
    List<TimeEntry> findByClockInBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);
    TimeEntry findTopByUserIdOrderByClockInDesc(Long userId);
}

