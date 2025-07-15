package com.backend;

import com.backend.authentication.AuthenticationRequest;
import com.backend.authentication.AuthenticationResponse;
import com.backend.authentication.AuthenticationService;
import com.backend.authentication.RegisterRequest;
import com.backend.config.JwtService;
import com.backend.dto.UserResponseDTO;
import com.backend.model.User;
import com.backend.enums.Role;
import com.backend.repository.UserRepository;
import com.backend.util.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private AuthenticationService authenticationService;

	private RegisterRequest registerRequest;
	private AuthenticationRequest authRequest;
	private User user;
	private final String testToken = "testToken";
	private final String testEmail = "test@example.com";

	@BeforeEach
	void setUp() {
		registerRequest = new RegisterRequest();
		registerRequest.setName("Test User");
		registerRequest.setEmail(testEmail);
		registerRequest.setPassword("password123");

		authRequest = new AuthenticationRequest();
		authRequest.setEmail(testEmail);
		authRequest.setPassword("password123");

		user = new User();
		user.setName("Test User");
		user.setEmail(testEmail);
		user.setPassword("encodedPassword");
		user.setRole(Role.valueOf(Constant.USER_ROLE));
	}

	// Tests for register method
	@Test
	void register_ShouldReturnAuthenticationResponse_WhenValidRequest() {
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(jwtService.generateToken(any(User.class), anyMap())).thenReturn(testToken);

		AuthenticationResponse response = authenticationService.register(registerRequest);

		assertNotNull(response);
		assertEquals(testToken, response.getToken());
		assertTrue(response.isValid());
		assertNotNull(response.getUser());
		assertEquals(registerRequest.getName(), response.getUser().getName());
		assertEquals(registerRequest.getEmail(), response.getUser().getEmail());

		verify(passwordEncoder).encode(registerRequest.getPassword());
		verify(userRepository).save(any(User.class));
		verify(jwtService).generateToken(any(User.class), anyMap());
	}

	@Test
	void register_ShouldSetUserRoleFromConstant() {
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
			User savedUser = invocation.getArgument(0);
			assertEquals(Role.valueOf(Constant.USER_ROLE), savedUser.getRole());
			return savedUser;
		});
		when(jwtService.generateToken(any(User.class), anyMap())).thenReturn(testToken);

		authenticationService.register(registerRequest);

		verify(userRepository).save(any(User.class));
	}

	// Tests for login method
	@Test
	void login_ShouldReturnAuthenticationResponse_WhenValidCredentials() {
		when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
		when(jwtService.generateToken(any(User.class), anyMap())).thenReturn(testToken);

		AuthenticationResponse response = authenticationService.login(authRequest);

		assertNotNull(response);
		assertEquals(testToken, response.getToken());
		assertTrue(response.isValid());
		assertNotNull(response.getUser());
		assertEquals(user.getName(), response.getUser().getName());
		assertEquals(user.getEmail(), response.getUser().getEmail());

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(userRepository).findByEmail(testEmail);
		verify(jwtService).generateToken(any(User.class), anyMap());
	}

	@Test
	void login_ShouldCallAuthenticationManagerWithCorrectCredentials() {
		when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
		when(jwtService.generateToken(any(User.class), anyMap())).thenReturn(testToken);

		authenticationService.login(authRequest);

		verify(authenticationManager).authenticate(
				new UsernamePasswordAuthenticationToken(
						authRequest.getEmail(),
						authRequest.getPassword()
				)
		);
	}


}