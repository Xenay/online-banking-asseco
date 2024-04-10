package asseco.praksa.OnlineBank.dto;

import org.springframework.http.ResponseEntity;

// Example DTO for the response
public class LoginResponseDto {
    private String message;
    private String token;

    // Constructor, getters, and setters
    public LoginResponseDto(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}