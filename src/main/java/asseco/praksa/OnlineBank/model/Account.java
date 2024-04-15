package asseco.praksa.OnlineBank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity // Marks this class as a JPA entity.
@Table(name = "account") // Specifies the table name.
public class Account {
    @Id // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the way the ID is generated.
    private Long id; // Adding an ID field to act as the primary key.
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String currency;

    // Constructors
    public Account() {}

    public Account(String accountNumber, String currency, String username) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.username = username;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }



    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
