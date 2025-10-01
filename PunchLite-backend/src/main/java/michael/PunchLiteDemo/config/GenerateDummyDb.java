package michael.PunchLiteDemo.config;


import michael.PunchLiteDemo.model.Role;
import michael.PunchLiteDemo.model.TimeEntry;
import michael.PunchLiteDemo.model.User;
import michael.PunchLiteDemo.repository.TimeEntryRepository;
import michael.PunchLiteDemo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
/**
 * 
 * This script fills in the database schema with randomized data to assist testing
 * 
 *      Generate 20 employees (username, password, email, wage, permissionSet.USER)
 *      Generate 2 managers (username, password, email, wage, permissionSet.MANAGER)
 *      Generate 1 admin (username, password, email, wage, permissionSet.ADMIN)
 *          For each employee, manager and admin generate 10 time entries (5 approved, 5 unapproved)

 */
@Component
public class GenerateDummyDb {

    private static final Random random = new Random();

    private final UserRepository userRepo;
    private final TimeEntryRepository timeEntryRepo;
    private final PasswordEncoder passwordEncoder;

    public GenerateDummyDb(UserRepository userRepo, TimeEntryRepository timeEntryRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.timeEntryRepo = timeEntryRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void populate(){
        this.wipeDb();
        ArrayList<User> managers = save2DummyManagers();
        this.save20DummyUsers(managers);
        this.saveDummyAdmin();
        this.userRepo.flush();
        System.out.println("Dummy data populated");
    }

    /** WARNING: delete the entire database,  RESET it for testing purposes */
    public void wipeDb(){
        this.userRepo.deleteAll();
        this.timeEntryRepo.deleteAll();
    }

    public void save20DummyUsers(ArrayList<User> managers){

        //generate 20 dummy users (first 10 get manager 0, second 10 get manager 1)
        for(int i = 0; i<20; i++){
            User u = new User();
            u.setUsername("employee" + i);
            u.setHourlyRate(random.nextDouble(100.0)+20);
            u.setPassword(passwordEncoder.encode("password"));
            u.setRole(Role.EMPLOYEE);
            if(i<10){

                u.setManager(managers.get(0));
                managers.get(0).addSubordinate(u);
            }else{
                u.setManager(managers.get(1));
                managers.get(1).addSubordinate(u);
            }
            this.userRepo.save(u); 

            //generate 20 time entries for each user (5 approved, 5 unapproved)
            save10DummyTimeEntries(u);
        }
    }

    public ArrayList<User> save2DummyManagers(){
        ArrayList<User> managers = new ArrayList<>();
        for(int i = 0; i<2; i++){
            User u = new User();
            u.setUsername("manager" + i);
            u.setHourlyRate(random.nextDouble(100.0) + 20);
            u.setPassword(passwordEncoder.encode("password"));
            u.setRole(Role.MANAGER);

            this.userRepo.save(u);
            managers.add(u);
            save10DummyTimeEntries(u);

        }
        return managers;
    }

    public void saveDummyAdmin(){
        User u = new User();
        u.setUsername("admin");
        u.setPassword(passwordEncoder.encode("password"));
        u.setRole(Role.ADMIN);
        this.userRepo.save(u);
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
            t.setClockOut(t.getClockIn().plusHours((long)(4+random.nextInt(5))));
            this.timeEntryRepo.save(t);
        }
    }
}
