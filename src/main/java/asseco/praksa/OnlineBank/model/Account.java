package asseco.praksa.OnlineBank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity // Marks this class as a JPA entity.
@Table(name = "account") // Specifies the table name.
public class Account {
    @Id // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the way the ID is generated.
    private Long id; // Adding an ID field to act as the primary key.

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance; // Changed from int to BigDecimal.

    @Column(nullable = false)
    private String currency;

    // Constructors
    public Account() {}

    public Account(String accountNumber, BigDecimal balance, String currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    // Getters and setters
    // Note: You'll need to update the balance's getter and setter to handle BigDecimal.
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
