package asseco.praksa.OnlineBank.model;

import asseco.praksa.OnlineBank.auth.EncryptionUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment_orders")
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "sender_iban")
    private String senderIban;

    @Column(name = "recipient_iban")
    private String recipientIban;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate = LocalDate.now();

    @Column(name = "payment_description")
    private String paymentDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account; // Assuming you have an Account entity

    @JoinColumn(name = "reciever_id", referencedColumnName = "id")
    private Long reciever; // Assuming you have an Account entity

    @Column(name = "payment_type")
    private String paymentType;


    // Constructors, Getters, and Setters
    public PaymentOrder() {}

    // All-args constructor
    public PaymentOrder(Long id, String senderIban, String recipientName, String recipientIban, BigDecimal amount, LocalDate transactionDate, String paymentDescription) {
        this.id = id;
        this.senderIban = senderIban;
        this.recipientName = recipientName;
        this.recipientIban = recipientIban;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.paymentDescription = paymentDescription;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipientName() {
        return EncryptionUtil.decrypt(recipientName);
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = EncryptionUtil.encrypt(recipientName);
    }

    public String getRecipientIban() {
        return EncryptionUtil.decrypt(recipientIban);
    }


    public void setRecipientIban(String recipientIban) {
        this.recipientIban = EncryptionUtil.encrypt(recipientIban);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentDescription() {
        return EncryptionUtil.decrypt(paymentDescription);
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = EncryptionUtil.encrypt(paymentDescription);
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public String getSenderIban() {
        return senderIban;
    }

    public void setSenderIban(String senderIban) {
        this.senderIban = senderIban;
    }
    public Long getRecieverId() {
        return reciever;
    }

    public void setRecieverId(Long recieverId) {
        this.reciever = recieverId;
    }

    public Long getReciever() {
        return reciever;
    }

    public void setReciever(Long reciever) {
        this.reciever = reciever;
    }

    public String getPaymenType() {
        return paymentType;
    }

    public void setPaymentType(String paymenType) {
        this.paymentType = paymenType;
    }
}

