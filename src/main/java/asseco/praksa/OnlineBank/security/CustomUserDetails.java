package asseco.praksa.OnlineBank.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections; // Import for an empty collection of GrantedAuthority

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    // Other fields as necessary

    // Constructor
    public CustomUserDetails(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // If your user has roles or permissions, return them here
        // For now, return an empty collection
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement logic to determine if the account is expired
        return true; // Assuming account never expires for simplicity
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement logic to determine if the account is locked
        return true; // Assuming account is never locked for simplicity
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement logic to determine if the credentials are expired
        return true; // Assuming credentials never expire for simplicity
    }

    @Override
    public boolean isEnabled() {
        // Implement logic to determine if the user is enabled
        return true; // Assuming user is always enabled for simplicity
    }
}
