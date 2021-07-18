package com.restapiwithjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restapiwithjwt.storage.repository.UserRepository;
import com.restapiwithjwt.util.ObjectsGenerator;
import com.uranus.garbags.client.java.model.Response;
import com.uranus.garbags.client.java.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AuhtenticationApiControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() {
    userRepository.deleteAllInBatch();
  }


  @Test
  public void userRegistrationShortUsernameBadRequest() throws Exception {
    User user = ObjectsGenerator.createUser();
    user.setUsername("inv");

    String body = getMapper().writeValueAsString(user);
    MvcResult result = mvc.perform(post("/registration")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest()).andReturn();

    Response response = fromJsonResponse(result.getResponse().getContentAsString());
    assertEquals(response.getDetails(), "*Username length must be between 6 and 32");
  }

  @Test
  public void userRegistrationAlreadyExistingUserBadRequest() throws Exception {
    User user = ObjectsGenerator.createUser();

    String body = getMapper().writeValueAsString(user);
    MvcResult result = mvc.perform(post("/registration")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk()).andReturn();

    result = mvc.perform(post("/registration")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest()).andReturn();

    Response response = fromJsonResponse(result.getResponse().getContentAsString());
    assertEquals(response.getDetails(), "*Username already exists");

  }

  @Test
  public void registerUserAuthenticate() throws Exception {
    User user = ObjectsGenerator.createUser();

    String body = getMapper().writeValueAsString(user);
    MvcResult result = mvc.perform(post("/registration")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk()).andReturn();


    result = mvc.perform(get("/api/authenticate?username=malikli.tural&password=secretpass")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk()).andReturn();

    String header = result.getResponse().getHeader("Authorization");
    header.contains("Bearer ey");
  }

  @Test
  public void authenticateBadCredentialsReturnsUnauthorized() throws Exception {
    User user = ObjectsGenerator.createUser();

    String body = getMapper().writeValueAsString(user);
    MvcResult result = mvc.perform(post("/registration")
            .contentType(APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk()).andReturn();


    result = mvc.perform(get("/api/authenticate?username=invalid&password=secretpass")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized()).andReturn();
  }

  ObjectMapper getMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  public static Response fromJsonResponse(String json) throws IOException {
    ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
    return om.readValue(json, Response.class);
  }
}
