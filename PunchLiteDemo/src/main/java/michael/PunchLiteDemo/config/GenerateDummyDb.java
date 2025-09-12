package michael.PunchLiteDemo.config;
import michael.PunchLiteDemo.model.Role;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.TimeEntryRepository;
import michael.PunchLiteDemo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Random;
/**
 * 
 * This script fills in the database schema with randomized data to assist testing
 * 
 *      Generate 20 employees (username, password, email, wage, permissionSet.USER)
 *      Generate 2 managers (username, password, email, wage, permissionSet.MANAGER)
 *      Generate 1 admin (username, password, email, wage, permissionSet.ADMIN)
 * 
 *      For each employee, manager and admin generate 10 time entries (5 approved, 5 unapproved)
 *         
 *      
 * 
 */
public class GenerateDummyDb {

    private static final Random random = new Random();

    private final UserRepository userRepo;
    private final TimeEntryRepository timeEntryRepo;

    public GenerateDummyDb(UserRepository userRepo, TimeEntryRepository timeEntryRepo){
        this.userRepo = userRepo;
        this.timeEntryRepo = timeEntryRepo;
    }

    public void populate(){
        this.wipeDb();
        this.save2DummyManagers();
        this.saveDummyAdmin();
        this.save20DummyUsers();
    }

    /** WARNING: delete the entire database,  RESET it for testing purposes */
    public void wipeDb(){
        this.userRepo.deleteAll();
        this.timeEntryRepo.deleteAll();
    }

    public void save20DummyUsers(){
        User manager0 = this.userRepo.findByUsername("manager0")
                        .orElseThrow(() -> new IllegalArgumentException("manager0 doesnt exist"));
        User manager1 = this.userRepo.findByUsername("manager1")
                        .orElseThrow(() -> new IllegalArgumentException("manager1 doesnt exist"));

        //generate 20 dummy users (first 10 get manager 0, second 10 get manager 1)
        for(int i = 0; i<20; i++){
            User u = new User();
            u.setUsername("employee" + i);
            u.setHourlyRate(random.nextDouble(100.0)+20);
            u.setPassword("password");
            u.setRole(Role.EMPLOYEE);
            if(i<10){
                u.setManager(manager0);
                manager0.addSubordinate(u);
            }else{
                u.setManager(manager1);
                manager1.addSubordinate(u);
            }
            this.userRepo.save(u); 

            //generate 20 time entries for each user (5 approved, 5 unapproved)
            save10DummyTimeEntries(u);
        }
    }

    public void save2DummyManagers(){
        for(int i = 0; i<2; i++){
            User u = new User();
            u.setUsername("manager" + i);
            u.setHourlyRate(random.nextDouble(100.0) + 20);
            u.setPassword("password");
            u.setRole(Role.MANAGER);
            save10DummyTimeEntries(u);
        }
    }

    public void saveDummyAdmin(){
        User u = new User();
        u.setUsername("admin");
        u.setPassword("password");
        u.setRole(Role.ADMIN);
    }

    public void save10DummyTimeEntries(User u){
        for(int j = 0; j<10; j++){
            TimeEntry t = new TimeEntry();
            t.setUser(u);
            if(j < 5){
                t.setIsValidated(true);
            }else{
                t.setIsValidated(false);
            }   
            //generate a random clock in and out time (4-8 hour shift)
            t.setClockIn(LocalDateTime.now().minusDays(random.nextInt(30)));
            t.setClockOut(t.getClockIn().plusHours(4+random.nextLong()));
            this.timeEntryRepo.save(t);
        }
    }
}
