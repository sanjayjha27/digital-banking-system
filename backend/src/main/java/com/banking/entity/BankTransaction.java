package com.banking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class BankTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Account account;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private TransactionType type;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal amount;
    @Column(nullable=false,precision=19,scale=2) private BigDecimal balanceAfter;
    private String relatedAccount;
    private String description;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public BankTransaction() {}
    public BankTransaction(Account a,TransactionType t,BigDecimal amount,BigDecimal balanceAfter,String relatedAccount,String description){this.account=a;this.type=t;this.amount=amount;this.balanceAfter=balanceAfter;this.relatedAccount=relatedAccount;this.description=description;}
    public Long getId(){return id;} public Account getAccount(){return account;} public TransactionType getType(){return type;} public BigDecimal getAmount(){return amount;} public BigDecimal getBalanceAfter(){return balanceAfter;} public String getRelatedAccount(){return relatedAccount;} public String getDescription(){return description;} public LocalDateTime getCreatedAt(){return createdAt;}
}
