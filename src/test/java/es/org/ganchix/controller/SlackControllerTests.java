package es.org.ganchix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.org.ganchix.SlackCommandLaunchApplication;
import es.org.ganchix.repository.PersonRepository;
import es.org.ganchix.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rafael Ríos on 21/02/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlackCommandLaunchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SlackControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Before
    public void setup() {
        personRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    public void testDoAPotacion_ShouldReturnThePotacionInARestaurant() throws Exception {

        MvcResult result = mockMvc.perform(post("/slack/command/potaciones")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .param("token", "slack_id")
                .param("user_id", "user_id")
                .param("user_name", "user_name")
                .param("text", "rest1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("response_type", is(notNullValue())))
                .andExpect(jsonPath("text", is(notNullValue())))
                .andExpect(jsonPath("attachments", is(notNullValue())))
                .andReturn();
    }

}
