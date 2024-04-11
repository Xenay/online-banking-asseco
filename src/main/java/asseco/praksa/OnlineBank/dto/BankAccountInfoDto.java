package asseco.praksa.OnlineBank.dto;


import asseco.praksa.OnlineBank.model.BankAccount;

import java.math.BigDecimal;

public class BankAccountInfoDto {
    private Long id;

    private String name;
    private BigDecimal balance;
    private String type;



    private String IBAN;
    // Constructor
    public BankAccountInfoDto(Long id, String name, BigDecimal balance, String type, String IBAN) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.IBAN = IBAN;
    }

    public BankAccountInfoDto(Long id, String name, BigDecimal balance, BankAccount.AccountType type, String IBAN) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.type = type.toString();
        this.IBAN = IBAN;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}
