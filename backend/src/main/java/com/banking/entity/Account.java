package com.banking.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table (name = "accounts")
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, unique = true, length = 20)
    private String accountNumber;
    @OneToOne (optional = false)
    private User user;
    @Column (nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    @Column (nullable = false)
    private boolean active = true;
    @Column (nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now ();

    public Account () {
    }

    public Account (String accountNumber, User user) {
        this.accountNumber = accountNumber;
        this.user = user;
    }

    public Long getId () {
        return id;
    }

    public String getAccountNumber () {
        return accountNumber;
    }

    public User getUser () {
        return user;
    }

    public BigDecimal getBalance () {
        return balance;
    }

    public boolean isActive () {
        return active;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public void setBalance (BigDecimal v) {
        balance = v;
    }

    public void setActive (boolean v) {
        active = v;
    }
}
