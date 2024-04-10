package asseco.praksa.OnlineBank.dto;


import asseco.praksa.OnlineBank.model.BankAccount;

import java.math.BigDecimal;

public class BankAccountInfoDto {

    private String name;
    private BigDecimal balance;
    private String type;

    // Constructor
    public BankAccountInfoDto(String name, BigDecimal balance, String type) {
        this.name = name;
        this.balance = balance;
        this.type = type;
    }

    public BankAccountInfoDto(String name, BigDecimal balance, BankAccount.AccountType type) {
        this.name = name;
        this.balance = balance;
        this.type = type.toString();
    }

    // Getters and Setters

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
}
