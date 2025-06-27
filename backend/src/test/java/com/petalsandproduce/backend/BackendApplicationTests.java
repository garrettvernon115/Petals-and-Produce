package com.petalsandproduce.backend;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc



class BackendApplicationTests {
	private Map<String,String> validRegistration;
	private Map<String,String> validLogin;
	private Map<String,String> invalidLogin;
	private Map<String,String> emptyPassword;
	private Map<String,String> emptyUsername;
	private Map<String,String> emptyEmail;
	private Map<String,String> missingPassword;
	private Map<String,String> missingEmail;
	private Map<String,String> incorrectPassword;
	private Map<String,String> incorrectEmail;

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach 
	void setup() {
		validRegistration = new HashMap<>();
		validRegistration.put("name", "John Computer");
		validRegistration.put("email", "johnc@gmail.com");
		validRegistration.put("password", "Password123");
		// Normal
		validLogin = new HashMap<>();
		validLogin.put("name", "John Computer");
		validLogin.put("email", "johnc@gmail.com");
		validLogin.put("password", "Password123");
		// User doesn't exist
		invalidLogin = new HashMap<>();
		invalidLogin.put("name", "AAAAAAAA");
		invalidLogin.put("password", "AAAAAAAA");
		// Basically self-documenting
		emptyPassword = new HashMap<>();
		emptyPassword.put("name", "Empty Password");
		emptyPassword.put("email", "emptypassword@gmail.com");
		emptyPassword.put("password", " ");

		emptyUsername = new HashMap<>();
		emptyUsername.put("name", " ");
		emptyUsername.put("email", "emptyname@gmail.com");
		emptyUsername.put("password", "Password123");

		emptyEmail = new HashMap<>();
		emptyEmail.put("name", "Empty Email");
		emptyEmail.put("email", " ");
		emptyEmail.put("password", "Password123");

		// Login stuff starts here so we need actual values for this

		missingPassword = new HashMap<>();
		missingPassword.put("name", "John Computer");
		missingPassword.put("email", "johnc@gmail.com");
		missingPassword.put("password", "");

		missingEmail = new HashMap<>();
		missingEmail.put("name", "John Computer");
		missingEmail.put("email", "");
		missingEmail.put("password", "Password123");

		incorrectPassword = new HashMap<>();
		incorrectPassword.put("name", "John Computer");
		incorrectPassword.put("email", "johnc@gmail.com");
		incorrectPassword.put("password", "wrong");

		incorrectEmail = new HashMap<>();
		incorrectEmail.put("name", "John Computer");
		incorrectEmail.put("email", "wrongemail@evil.com");
		incorrectEmail.put("password", "Password123");
	}

	@Test
	void contextLoads() {
	}

	// Registration section 

	@Test
	public void testSuccessfulRegistration() throws Exception {
		// It doesn't matter if this throws an exception. 
		// We just need a clean slate because otherwise it starts being really annoying
		mvc.perform(delete("/api/deleteAccount")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(validRegistration)));

		mvc.perform(post("/api/register")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(validRegistration)))
		.andExpect(status().isOk());
	}

	@Test
	public void testEmptyPassword() throws Exception {
		mvc.perform(delete("/api/deleteAccount")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyPassword)));

		mvc.perform(post("/api/register")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyPassword)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testEmptyUsername() throws Exception {
		mvc.perform(delete("/api/deleteAccount")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyUsername)));

		mvc.perform(post("/api/register")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyUsername)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testEmptyEmail() throws Exception {
		mvc.perform(delete("/api/deleteAccount")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyEmail)));

		mvc.perform(post("/api/register")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(emptyEmail)))
		.andExpect(status().isUnauthorized());
	}

	// Login section

	@Test
	public void testSuccessfulLogin() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(validLogin)))
		.andExpect(status().isOk());
	}

	@Test
	public void testUnsuccessfulRegistration() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(invalidLogin)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testIncorrectPassword() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(incorrectPassword)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testIncorrectEmail() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(incorrectEmail)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testMissingPassword() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(missingPassword)))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testMissingEmail() throws Exception {
		mvc.perform(post("/api/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(missingEmail)))
		.andExpect(status().isUnauthorized());
	}
}
