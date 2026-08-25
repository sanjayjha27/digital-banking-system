package com.banking.dto;
import com.banking.entity.*; import java.math.BigDecimal; import java.time.LocalDateTime;
public record TransactionResponse(Long id,TransactionType type,BigDecimal amount,BigDecimal balanceAfter,String relatedAccount,String description,LocalDateTime createdAt){}
