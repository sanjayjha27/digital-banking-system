package com.banking.dto;
import java.math.BigDecimal;import java.time.LocalDateTime;
public record AccountResponse(String accountNumber,String fullName,String email,BigDecimal balance,boolean active,LocalDateTime createdAt){}
