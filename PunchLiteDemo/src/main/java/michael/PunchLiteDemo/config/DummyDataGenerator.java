package michael.PunchLiteDemo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyDataGenerator implements CommandLineRunner {
    
    private final GenerateDummyDb gen;

    public DummyDataGenerator(GenerateDummyDb gen){
        this.gen = gen;
    }
    public void run(String... args) throws Exception {
        gen.populate();
    }






    
}
