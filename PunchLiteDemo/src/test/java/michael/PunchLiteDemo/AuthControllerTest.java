package michael.PunchLiteDemo;

//use logger compatible with spring boot
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import michael.PunchLiteDemo.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup(TestInfo testInfo) {
        logger.info("Test started: " + testInfo.getDisplayName());
    }

    /**
     * Test if users can register a new account
     * -Generate a random user
     * -Insert them into the database and collect response
     * -@Rollback deletes the random user after test
     * 
     */
    @Test
    public void registerUser_ShouldReturnOk_WhenValidDataProvided() throws Exception {

        //generate a random user
        User user = new User();
        user.setUsername("testuser" + System.currentTimeMillis());
        user.setPassword("password");

        //register the user
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.message").value("User successfully registered"));
        
    }
    
}
