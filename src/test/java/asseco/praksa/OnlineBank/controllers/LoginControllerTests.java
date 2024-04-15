package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.LoginRequest;
import asseco.praksa.OnlineBank.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTests {

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest("luka2", "123456");
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("luka2", "123456"))).thenReturn(auth);
        when(tokenProvider.generateToken(auth)).thenReturn("token");

        ResponseEntity<?> response = loginController.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("token"));
    }

    @Test
    void loginFailureBadCredentials() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));
        LoginRequest loginRequest = new LoginRequest("user", "password");

        ResponseEntity<?> response = loginController.login(loginRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void loginFailureException() {
        when(authenticationManager.authenticate(any())).thenThrow(RuntimeException.class);
        LoginRequest loginRequest = new LoginRequest("user", "password");

        ResponseEntity<?> response = loginController.login(loginRequest);

        assertEquals(500, response.getStatusCodeValue());
    }
}
