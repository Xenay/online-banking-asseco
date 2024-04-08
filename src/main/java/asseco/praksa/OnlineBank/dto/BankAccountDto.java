package asseco.praksa.OnlineBank.dto;

import java.math.BigDecimal;

public class BankAccountDto {

    private String name;
    private String type;
    private BigDecimal minimumBalance;
    private Long accountId; // Use this field to capture account_id from the payload

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
