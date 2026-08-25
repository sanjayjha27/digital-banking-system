package com.banking.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, unique = true)
    private String email;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    private String fullName;
    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Role role = Role.USER;
    @Column (nullable = false)
    private boolean enabled = true;
    @Column (nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now ();

    public User () {
    }

    public User (String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Long getId () {
        return id;
    }

    public String getEmail () {
        return email;
    }

    public String getPassword () {
        return password;
    }

    public String getFullName () {
        return fullName;
    }

    public Role getRole () {
        return role;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public void setEmail (String v) {
        email = v;
    }

    public void setPassword (String v) {
        password = v;
    }

    public void setFullName (String v) {
        fullName = v;
    }

    public void setRole (Role v) {
        role = v;
    }

    public void setEnabled (boolean v) {
        enabled = v;
    }
}
